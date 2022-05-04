package com.rant.sfbackend.services;

import com.rant.sfbackend.DTO.ProductRequest;
import com.rant.sfbackend.DTO.ProductResponse;
import com.rant.sfbackend.factories.ProductFactory;
import com.rant.sfbackend.model.Product;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.repositories.ProductRepository;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductFactory productFactory;
    private final JWTUtil jwtUtil;

    @Autowired
    public ProductService(UserRepository userRepository, ProductRepository productRepository,
                          ProductFactory productFactory, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productFactory = productFactory;
        this.jwtUtil = jwtUtil;
    }

    public ProductResponse createProduct(ProductRequest productRequest, HttpServletRequest httpServletRequest)
            throws UsernameNotFoundException {
        String jwt = httpServletRequest.getHeader("Authorization").split(" ")[1];
        String userEmail = jwtUtil.extractUsername(jwt);
        User owner = userRepository.getUserByEmail(userEmail);
        if (owner.getEmail() == null)
            throw new UsernameNotFoundException("User with this id wasn't found");

        Product product = productFactory.createProduct(productRequest, owner);
        productRepository.save(product);

        return new ProductResponse(product);
    }
}
