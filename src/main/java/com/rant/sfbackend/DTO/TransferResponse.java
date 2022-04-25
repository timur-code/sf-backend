package com.rant.sfbackend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class TransferResponse {
    private String userFrom;
    private String userTo; //email or phone number
    private Long amount;
    private LocalDateTime dateTime;

    public TransferResponse(String userFrom, String userTo, Long amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
        this.dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
