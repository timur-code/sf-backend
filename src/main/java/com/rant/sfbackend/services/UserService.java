package com.rant.sfbackend.services;

import com.rant.sfbackend.factories.UserFactory;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.requestForm.UserRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userFactory = UserFactory.getInstance();
    }

    public JSONObject createUser(String email, String fullName,
                                 String phone, String password) {
        User newUser = userFactory.createUser(email, fullName, phone, password);

        userRepository.save(newUser);

        JSONObject userJson = new JSONObject();
        userJson.appendField("email", newUser.getEmail());
        userJson.appendField("fullName", newUser.getFullName());
        userJson.appendField("phone", newUser.getPhone());

        return userJson;
    }

    public JSONObject createUser(UserRequest userRequest) {
        User newUser = userFactory.createUser(userRequest);

        userRepository.save(newUser);

        JSONObject userJson = new JSONObject();
        userJson.appendField("email", newUser.getEmail());
        userJson.appendField("fullName", newUser.getFullName());
        userJson.appendField("phone", newUser.getPhone());

        return userJson;
    }
}
