package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.ProductRequest;
import com.rant.sfbackend.DTO.ProductResponse;
import com.rant.sfbackend.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(productResponse);
    }
}
