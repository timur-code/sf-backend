package com.rant.sfbackend.factories;

import com.rant.sfbackend.helpers.Pair;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.model.Wallet;
import com.rant.sfbackend.DTO.UserRequest;
import com.rant.sfbackend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class UserFactory {
    private final RoleRepository roleRepository;

    @Autowired
    public UserFactory(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User createUser(String email, String fullName,
                           String phone, String password) {

        return new User(email, fullName, phone, password);
    }

    public Pair<User, Wallet> createUser(UserRequest userRequest) {
        User newUser = new User(userRequest.getEmail(), userRequest.getFullName(),
                userRequest.getPhone(), userRequest.getPassword());

        newUser.getRoles().add(roleRepository.findByAuthority("user"));

        Wallet wallet = new Wallet();

        newUser.setWallet(wallet);

        Pair<User, Wallet> pair = new Pair<>(newUser, wallet);

        return pair;
    }
}
