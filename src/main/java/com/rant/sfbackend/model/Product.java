package com.rant.sfbackend.model;

import com.rant.sfbackend.DTO.ProductRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imgLink;
    private String description;
    private Integer price;
    @ManyToOne
    private User owner;
    private boolean isBought;

    public Product (ProductRequest productRequest, User owner) {
        this.name = productRequest.getName();
        this.imgLink = productRequest.getImgLink();
        this.description = productRequest.getDescription();
        this.price = productRequest.getPrice();
        this.owner = owner;
        this.isBought = false;
    }
}
