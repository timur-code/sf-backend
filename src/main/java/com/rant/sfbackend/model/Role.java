package com.rant.sfbackend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String role;
}
