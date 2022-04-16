package com.rant.sfbackend.services;

import com.rant.sfbackend.DTO.*;
import com.rant.sfbackend.factories.UserFactory;
import com.rant.sfbackend.helpers.Pair;
import com.rant.sfbackend.model.User;
import com.rant.sfbackend.model.Wallet;
import com.rant.sfbackend.repositories.UserRepository;
import com.rant.sfbackend.repositories.WalletRepository;
import com.rant.sfbackend.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       WalletRepository walletRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.userFactory = UserFactory.getInstance();
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(UserRequest userRequest) throws InstanceAlreadyExistsException {
        if(userRepository.getUserByEmail(userRequest.getEmail()) != null ||
                userRepository.getUserByPhone(userRequest.getPhone()) != null) {
            throw new InstanceAlreadyExistsException("User with this email or phone already exists");
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Pair<User, Wallet> pair = userFactory.createUser(userRequest);

        walletRepository.save(pair.getSecond());
        userRepository.save(pair.getFirst());
    }

    public UserResponse getOwnInfo(HttpServletRequest request) throws UsernameNotFoundException{
        String jwt = request.getHeader("Authorization").split(" ")[1];

        System.out.println(jwt);

        String userEmail = jwtUtil.extractUsername(jwt);
        UserResponse userResponse = new UserResponse(userRepository.getUserByEmail(userEmail));

        if(userResponse.getEmail() != null)
            return userResponse;

        throw new UsernameNotFoundException("Could not find user's data");
    }

    public void deposit(Long userId, DepositRequest depositRequest, HttpServletRequest request) throws Exception{
        String jwt = request.getHeader("Authorization").split(" ")[1];

        String email = jwtUtil.extractUsername(jwt);

        User user = userRepository.getById(userId);

        if(!Objects.equals(user.getEmail(), email))
            throw new Exception("You can't deposit/withdraw to/from another user.");

        Wallet wallet = user.getWallet();

        wallet.deposit(depositRequest.getAmount());

        walletRepository.save(wallet);
    }

    public void withdraw(Long userId, WithdrawRequest withdrawRequest, HttpServletRequest request)
            throws IllegalAccessException, ArithmeticException {
        String jwt = request.getHeader("Authorization").split(" ")[1];

        String email = jwtUtil.extractUsername(jwt);

        User user = userRepository.getById(userId);

        if(!Objects.equals(user.getEmail(), email))
            throw new IllegalAccessException("You can't deposit/withdraw to/from another user.");

        Wallet wallet = user.getWallet();

        if(wallet.withdraw(withdrawRequest))
            throw new ArithmeticException("Not enough funds.");
        walletRepository.save(wallet);
    }

    public WalletResponse getBalance(Long userId, HttpServletRequest request)
            throws IllegalAccessException {
        String jwt = request.getHeader("Authorization").split(" ")[1];

        String email = jwtUtil.extractUsername(jwt);

        User user = userRepository.getById(userId);

        if(!Objects.equals(user.getEmail(), email))
            throw new IllegalAccessException("You can't see balance of another user.");

        return new WalletResponse(user.getWallet());
    }
}
