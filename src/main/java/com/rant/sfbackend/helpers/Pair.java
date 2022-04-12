package com.rant.sfbackend.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair <T,V>{
    private final T first;
    private final V second;
}
