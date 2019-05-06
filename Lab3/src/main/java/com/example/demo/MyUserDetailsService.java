package com.example.demo;

import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.text.html.Option;
import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Utente loadUserByUsername(String username){
        Optional<Utente> user = userRepository.findById(username);
        if(user.isPresent())
            return user.get();
        throw new RuntimeException();
    }
}
