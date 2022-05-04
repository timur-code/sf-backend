package com.rant.sfbackend.DTO;

import com.rant.sfbackend.model.Product;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String imgLink;
    private String description;
    private Integer price;
    private UserResponse owner;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.imgLink = product.getImgLink();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.owner = new UserResponse(product.getOwner());
    }
}
