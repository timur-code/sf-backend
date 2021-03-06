package com.rant.sfbackend.model;

import com.rant.sfbackend.DTO.WithdrawRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
@Data
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private Long balance;

    public Wallet() {
        balance = 0L;
    }

    public boolean withdraw(WithdrawRequest withdrawRequest) {
        if(withdrawRequest.getAmount() <= this.balance) {
            this.balance -= withdrawRequest.getAmount();
            return true;
        }
        return false;
    }

    public boolean withdraw(Long amount) {
        if(amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(Long amount) {
        this.balance += amount;
    }
}
