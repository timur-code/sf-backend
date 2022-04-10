package com.rant.sfbackend.controllers;

import com.rant.sfbackend.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public JSONObject createUser(@RequestBody String email, @RequestBody String fullName,
                                 @RequestBody String phone, @RequestBody String password) {
        return userService.createUser(email, fullName, phone , password);
    }
}
