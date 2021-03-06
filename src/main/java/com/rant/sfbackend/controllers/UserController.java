package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.*;
import com.rant.sfbackend.services.ProductService;
import com.rant.sfbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
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

    @PostMapping("/{userId}/transfer")
    public ResponseEntity<?> transferMoney (@PathVariable(value = "userId") Long userId,
                                            @RequestBody TransferRequest transferRequest, HttpServletRequest request) {
        TransferResponse transferResponse = new TransferResponse();
        try {
            transferResponse = userService.transferMoney(userId, transferRequest, request);
        } catch (IllegalAccessException | UsernameNotFoundException | ArithmeticException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<?> productsFromUser (HttpServletRequest request) {
        List<ProductResponse> products;
        try {
            products = productService.getProductsFromUser(request);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(products);
    }
}
