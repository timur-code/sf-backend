package com.rant.sfbackend.repositories;

import com.rant.sfbackend.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
