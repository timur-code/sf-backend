package com.rant.sfbackend.DTO;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String imgLink;
    private String description;
    private Integer price;
}