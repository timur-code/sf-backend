package com.rant.sfbackend.repositories;

import com.rant.sfbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByCategoryName(String categoryName);
}
