package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.DepositRequest;
import com.rant.sfbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/deposit")
    public ResponseEntity<?> depositToUser (@PathVariable(value = "userId") Long userId, @RequestBody DepositRequest depositRequest) {
        userService.deposit(userId, depositRequest);

        return ResponseEntity.ok("Successful deposit.");
    }
}
