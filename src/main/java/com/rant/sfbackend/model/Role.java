package com.rant.sfbackend.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
