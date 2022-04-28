package com.rant.sfbackend;

import com.rant.sfbackend.model.Role;
import com.rant.sfbackend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfBackendApplication implements CommandLineRunner {
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SfBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.findByAuthority("user") == null)
			roleRepository.save(new Role("user"));

		if(roleRepository.findByAuthority("admin") == null)
			roleRepository.save(new Role("admin"));
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
