package com.rant.sfbackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String fullName;
    private String phone;
    @ToString.Exclude
    private String password;
    private Boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToOne
    private Wallet wallet;
    @ManyToMany
    private List<Product> cart;

    public User(String email, String fullName,
                String phone, String password) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.active = true;
        this.roles = new ArrayList<>();
        this.cart = new ArrayList<>();
    }
}
