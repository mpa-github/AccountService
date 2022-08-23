package com.pavelmuravyev.accountservice.configurations;

import com.pavelmuravyev.accountservice.repositories.UserRepository;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.configurations.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        User user = optUser.get();
        return new UserDetailsImpl(user);
    }
}
