package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.DepositRequest;
import com.rant.sfbackend.DTO.WalletResponse;
import com.rant.sfbackend.DTO.WithdrawRequest;
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
    public ResponseEntity<?> depositToUser (@PathVariable(value = "userId") Long userId,
                                            @RequestBody DepositRequest depositRequest, HttpServletRequest request) {
        try {
            userService.deposit(userId, depositRequest, request);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Successful deposit.");
    }

    @PostMapping("/{userId}/withdraw")
    public ResponseEntity<?> withdrawFromUser (@PathVariable(value = "userId") Long userId,
                                               @RequestBody WithdrawRequest withdrawRequest, HttpServletRequest request) {
        try {
            userService.withdraw(userId, withdrawRequest, request);
        } catch (IllegalAccessException | ArithmeticException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Successful withdrawal.");
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<?> getUserBalance (@PathVariable(value = "userId") Long userId, HttpServletRequest request) {
        WalletResponse walletResponse;
        try {
            walletResponse = userService.getBalance(userId, request);
        } catch (IllegalAccessException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(walletResponse);
    }
}
