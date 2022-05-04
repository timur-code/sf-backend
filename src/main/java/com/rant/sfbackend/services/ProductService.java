package com.rant.sfbackend.services;

import com.rant.sfbackend.DTO.ProductRequest;
import com.rant.sfbackend.DTO.ProductResponse;
import com.rant.sfbackend.factories.ProductFactory;
import com.rant.sfbackend.model.Product;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.repositories.ProductRepository;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.repositories.WalletRepository;
import com.rant.sfbackend.utils.JWTUtil;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;
    private final ProductFactory productFactory;
    private final JWTUtil jwtUtil;

    @Autowired
    public ProductService(UserRepository userRepository, ProductRepository productRepository,
                          WalletRepository walletRepository, ProductFactory productFactory, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.walletRepository = walletRepository;
        this.productFactory = productFactory;
        this.jwtUtil = jwtUtil;
    }

    public ProductResponse createProduct(ProductRequest productRequest, HttpServletRequest httpServletRequest)
            throws UsernameNotFoundException {
        User owner = verifyAndGetUser(httpServletRequest);

        Product product = productFactory.createProduct(productRequest, owner);
        productRepository.save(product);

        return new ProductResponse(product);
    }

    public ProductResponse getProduct(Long productId) throws NotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new NotFoundException("Product was not found.");

        if (product.get().isBought())
            throw new NotFoundException("Product is already bought.");

        return new ProductResponse(product.get());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products) {
            if(!product.isBought())
                productResponses.add(new ProductResponse(product));
        }
        return productResponses;
    }

    public List<ProductResponse> getProductsFromUser (HttpServletRequest request) throws UsernameNotFoundException {
        User user = verifyAndGetUser(request);

        List<Product> products = productRepository.findAllByOwnerId(user.getId());
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products) {
            if(!product.isBought())
                productResponses.add(new ProductResponse(product));
        }
        return productResponses;
    }

    public List<ProductResponse> getProductCart(HttpServletRequest request) throws UsernameNotFoundException {
        User user = verifyAndGetUser(request);

        List<ProductResponse> products = new ArrayList<>();
        for(Product product: user.getCart())
            products.add(new ProductResponse(product));

        return products;
    }

    public ProductResponse addProductToUserCart(Long productId, HttpServletRequest request) throws NotFoundException, UsernameNotFoundException {
        User user = verifyAndGetUser(request);

        Product product = verifyAndGetProduct(productId);

        user.getCart().add(product);
        product.setBought(true);
        productRepository.save(product);
        userRepository.save(user);

        return new ProductResponse(product);
    }

    public void buyProducts (HttpServletRequest request) throws UsernameNotFoundException, ArithmeticException {
        User user = verifyAndGetUser(request);
        Long total = 0L;

        for (Product product: user.getCart())
            total += product.getPrice().longValue();

        log.info(total.toString());

        if (!user.getWallet().withdraw(total))
            throw new ArithmeticException("Not enough funds to buy.");

        for (Product product: user.getCart())
            product.setBought(true);

        walletRepository.save(user.getWallet());
        productRepository.saveAll(user.getCart());
        user.getCart().clear();
        userRepository.save(user);
    }

    private User verifyAndGetUser(HttpServletRequest request) throws UsernameNotFoundException {
        String jwt = request.getHeader("Authorization").split(" ")[1];
        String userEmail = jwtUtil.extractUsername(jwt);
        User user = userRepository.getUserByEmail(userEmail);
        if (user.getEmail() == null)
            throw new UsernameNotFoundException("User with this id wasn't found");
        return user;
    }

    private Product verifyAndGetProduct(Long productId) throws NotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new NotFoundException("Product was not found.");

        if (product.get().isBought())
            throw new NotFoundException("Product is already bought.");

        return product.get();
    }
}
