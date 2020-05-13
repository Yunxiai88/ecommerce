package com.sistic.ecommerce.services;

import java.util.Optional;

import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        Optional<User> users = userRepository.findByEmail(email);
        return users;
    }

}