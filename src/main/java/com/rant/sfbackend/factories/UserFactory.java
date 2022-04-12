package com.rant.sfbackend.factories;

import com.rant.sfbackend.helpers.Pair;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.model.Wallet;
import com.rant.sfbackend.DTO.UserRequest;
import org.springframework.stereotype.Service;

@Service
public final class UserFactory {

    public UserFactory() {
    }

    private static volatile UserFactory instance;

    public static UserFactory getInstance() {
        UserFactory result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserFactory.class) {
            if(instance == null) {
                instance = new UserFactory();
            }
            return instance;
        }
    }

    public User createUser(String email, String fullName,
                           String phone, String password) {

        return new User(email, fullName, phone, password);
    }

    public Pair<User, Wallet> createUser(UserRequest userRequest) {
        User newUser = new User(userRequest.getEmail(), userRequest.getFullName(),
                userRequest.getPhone(), userRequest.getPassword());

        Wallet wallet = new Wallet();

        newUser.setWallet(wallet);

        Pair<User, Wallet> pair = new Pair<>(newUser, wallet);

        return pair;
    }
}
