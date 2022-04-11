package com.rant.sfbackend.services;

import com.rant.sfbackend.factories.UserFactory;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.requestForm.UserRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userFactory = UserFactory.getInstance();
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        User newUser = new User(userRequest.getEmail(), userRequest.getFullName(),
                userRequest.getPhone(), encodedPassword);

        userRepository.save(newUser);
    }
}
