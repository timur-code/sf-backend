package com.rant.sfbackend.factories;

import com.rant.sfbackend.model.User;
import com.rant.sfbackend.requestForm.UserRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public final class UserFactory {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(password);

        return new User(email, fullName, phone, encodedPassword);
    }

    public User createUser(UserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        return new User(userRequest.getEmail(), userRequest.getFullName(),
                userRequest.getPhone(), encodedPassword);
    }
}
