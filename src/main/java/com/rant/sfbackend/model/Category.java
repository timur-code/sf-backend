package com.rant.sfbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
