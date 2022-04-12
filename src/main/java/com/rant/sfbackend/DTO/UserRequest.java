package com.rant.sfbackend.DTO;

import lombok.*;

@Data
public class UserRequest {
    private String email;
    private String fullName;
    private String phone;
    private String password;
}
