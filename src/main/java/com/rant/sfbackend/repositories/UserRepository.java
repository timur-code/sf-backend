package com.rant.sfbackend.repositories;

import com.rant.sfbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserByEmail(String email);

    public User getUserByPhone(String phone);
}
