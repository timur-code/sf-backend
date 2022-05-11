package com.rant.sfbackend;

import com.rant.sfbackend.model.Category;
import com.rant.sfbackend.model.Role;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.model.Wallet;
import com.rant.sfbackend.repositories.CategoryRepository;
import com.rant.sfbackend.repositories.RoleRepository;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SfBackendApplication implements CommandLineRunner {
	private RoleRepository roleRepository;
	private CategoryRepository categoryRepository;
	private UserRepository userRepository;
	private WalletRepository walletRepository;

	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SfBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.findByAuthority("user") == null)
			roleRepository.save(new Role("user"));

		if(roleRepository.findByAuthority("admin") == null)
			roleRepository.save(new Role("admin"));

		if(categoryRepository.findByCategoryName("Electronics") == null)
			categoryRepository.save(new Category("Electronics"));

		if(categoryRepository.findByCategoryName("Fashion") == null)
			categoryRepository.save(new Category("Fashion"));

		if(categoryRepository.findByCategoryName("Health") == null)
			categoryRepository.save(new Category("Health"));

		if(categoryRepository.findByCategoryName("For Home") == null)
			categoryRepository.save(new Category("For Home"));

		if(categoryRepository.findByCategoryName("Sports") == null)
			categoryRepository.save(new Category("Sports"));

		if(categoryRepository.findByCategoryName("Art") == null)
			categoryRepository.save(new Category("Art"));

		if(userRepository.getUserByEmail("admin@mail.com") == null) {
			Wallet wallet = new Wallet();
			User admin = new User("admin@mail.com", "admin", "+7", passwordEncoder.encode("admin"));
			admin.getRoles().add(roleRepository.findByAuthority("admin"));
			admin.getRoles().add(roleRepository.findByAuthority("user"));
			admin.setWallet(wallet);
			walletRepository.save(wallet);
			userRepository.save(admin);
		}
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setWalletRepository(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
