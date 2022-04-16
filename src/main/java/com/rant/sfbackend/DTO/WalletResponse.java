package com.rant.sfbackend.DTO;

import com.rant.sfbackend.model.Wallet;
import lombok.Data;

@Data
public class WalletResponse {
    private Long balance;

    public WalletResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
    }
}
