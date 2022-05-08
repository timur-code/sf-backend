package com.rant.sfbackend.services;

import com.rant.sfbackend.model.Category;
import com.rant.sfbackend.model.Role;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.repositories.CategoryRepository;
import com.rant.sfbackend.repositories.RoleRepository;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

@Service
public class AdminService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTUtil jwtUtil;


    @Autowired
    public AdminService(CategoryRepository categoryRepository, UserRepository userRepository,
                        RoleRepository roleRepository, JWTUtil jwtUtil) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    public void addNewCategory(String categoryName, HttpServletRequest request)
            throws InstanceAlreadyExistsException, UsernameNotFoundException {
        verifyAdminRequest(request);

        if(categoryRepository.findByCategoryName(categoryName) != null)
            throw new InstanceAlreadyExistsException("Category already exists.");

        categoryRepository.save(new Category(categoryName));
    }

    public void verifyAdminRequest (HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").split(" ")[1];
        String userEmail = jwtUtil.extractUsername(jwt);
        User user = userRepository.getUserByEmail(userEmail);
        Role admin = roleRepository.findByAuthority("admin");
        if (user.getEmail() == null || !user.getRoles().contains(admin))
            throw new UsernameNotFoundException("User with this id wasn't found or not enough authorities.");
    }
}
