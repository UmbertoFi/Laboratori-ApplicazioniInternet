package com.example.demo;

import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UtenteRuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UtenteRuoloRepository utenteRuoloRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Utente> user = userRepository.findById(username);
        if (user.isPresent() == false) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user.get());
    }
}
