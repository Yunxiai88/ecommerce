package com.sistic.ecommerce.security;

import java.util.Optional;

import javax.annotation.Resource;

import com.sistic.ecommerce.model.CustomSessionScope;
import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Resource(name = "customSessionScope")
    CustomSessionScope customSessionScope;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        // set user into session scope
        if (!Optional.ofNullable(customSessionScope.getUser()).isPresent()) {
            customSessionScope.setUser(userOptional.get());
        }

        return UserDetailsImpl.create(userOptional.get());
    }

}