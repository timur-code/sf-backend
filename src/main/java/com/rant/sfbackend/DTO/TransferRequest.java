package com.rant.sfbackend.DTO;

import lombok.Data;

@Data
public class TransferRequest {
    private String userTo; //email or phone
    private Long amount;
}
