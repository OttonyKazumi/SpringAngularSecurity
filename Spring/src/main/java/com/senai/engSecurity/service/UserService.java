package com.senai.engSecurity.service;

import com.senai.engSecurity.model.User;
import com.senai.engSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(User user) {
        User foundUser = this.userRepository
            .findByUsername(user.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Verifica se a senha fornecida corresponde à senha criptografada no banco de dados
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return foundUser;
        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }
    }
}
