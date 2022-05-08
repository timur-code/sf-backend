package com.rant.sfbackend;

import com.rant.sfbackend.model.Category;
import com.rant.sfbackend.model.Role;
import com.rant.sfbackend.repositories.CategoryRepository;
import com.rant.sfbackend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfBackendApplication implements CommandLineRunner {
	private RoleRepository roleRepository;
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SfBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.findByAuthority("user") == null)
			roleRepository.save(new Role("user"));

		if(roleRepository.findByAuthority("admin") == null)
			roleRepository.save(new Role("admin"));

		if(categoryRepository.findByCategoryName("TV") == null)
			categoryRepository.save(new Category("TV"));

		if(categoryRepository.findByCategoryName("PC") == null)
			categoryRepository.save(new Category("PC"));

		if(categoryRepository.findByCategoryName("Games") == null)
			categoryRepository.save(new Category("Games"));
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
