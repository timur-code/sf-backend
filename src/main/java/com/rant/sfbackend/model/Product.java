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
    @ManyToOne
    private Category category;
    private Integer price;
    @ManyToOne
    private User owner;
    private boolean isBought;
    private boolean isInCart;
    @ManyToOne
    private User buyer;

    public Product (ProductRequest productRequest, User owner, Category category) {
        this.name = productRequest.getName();
        this.imgLink = productRequest.getImgLink();
        this.description = productRequest.getDescription();
        this.category = category;
        this.price = productRequest.getPrice();
        this.owner = owner;
        this.isBought = false;
        this.isInCart = false;
        this.buyer = null;
    }
}
