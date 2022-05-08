package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.ProductRequest;
import com.rant.sfbackend.DTO.ProductResponse;
import com.rant.sfbackend.services.ProductService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/market")
@Slf4j
public class MarketController {
    private final ProductService productService;

    @Autowired
    public MarketController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest,
                                        HttpServletRequest httpServletRequest) {
        ProductResponse productResponse;
        try {
            productResponse = productService.createProduct(productRequest, httpServletRequest);
        } catch (UsernameNotFoundException | NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{productId}/get")
    public ResponseEntity<?> getProduct(@PathVariable(value = "productId") Long productId) {
        ProductResponse productResponse;
        try {
            productResponse = productService.getProduct(productId);
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> productResponseList = productService.getAllProducts();
        return ResponseEntity.ok(productResponseList);
    }

    @PostMapping("/{productId}/add")
    public ResponseEntity<?> addToCart(@PathVariable(value = "productId") Long productId, HttpServletRequest request) {
        ProductResponse product;
        try {
            product = productService.addProductToUserCart(productId, request);
        } catch (UsernameNotFoundException | NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/cart/{productId}/remove")
    public ResponseEntity<?> removeItemFromCart(@PathVariable(value = "productId") Long productId,HttpServletRequest request) {
        ProductResponse product;
        try {
            product = productService.removeProductFromUserCart(productId, request);
        } catch (UsernameNotFoundException | NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        List<ProductResponse> cart;
        try {
            cart = productService.getProductCart(request);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/cart/buy")
    public ResponseEntity<?> buyCart (HttpServletRequest request) {
        try {
            productService.buyProducts(request);
        } catch (UsernameNotFoundException | ArithmeticException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Transaction is successful");
    }
}
