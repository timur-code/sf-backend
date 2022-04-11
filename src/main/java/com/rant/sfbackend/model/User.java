package com.rant.sfbackend.model;

import com.rant.sfbackend.requestForm.UserRequest;
import lombok.*;

import javax.persistence.*;
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
    private String roles;
//    private Wallet wallet;
//    private List<Product> productList;

    public User(String email, String fullName,
                String phone, String password) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.active = true;
        this.roles = "user";
    }

    //TODO: add wallet functionality
}
