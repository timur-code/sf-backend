package com.rant.sfbackend.DTO;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String imgLink;
    private String description;
    private String categoryName;
    private Integer price;
}
