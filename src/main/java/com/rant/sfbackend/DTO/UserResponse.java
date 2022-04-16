package com.rant.sfbackend.DTO;

import com.rant.sfbackend.model.User;
import com.rant.sfbackend.model.Wallet;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private Boolean active;
    private String roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.active = user.getActive();
        this.roles = user.getRoles();
    }
}
