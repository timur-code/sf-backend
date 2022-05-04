package com.rant.sfbackend.factories;

import com.rant.sfbackend.DTO.ProductRequest;
import com.rant.sfbackend.model.Product;
import com.rant.sfbackend.model.User;
import org.springframework.stereotype.Service;

@Service
public class ProductFactory {

    public Product createProduct(ProductRequest productRequest, User owner) {
        return new Product(productRequest, owner);
    }
}
