package com.rant.sfbackend.requestForm;

import lombok.*;

@Data
public class UserRequest {
    private String email;
    private String fullName;
    private String phone;
    @ToString.Exclude
    private String password;
}
