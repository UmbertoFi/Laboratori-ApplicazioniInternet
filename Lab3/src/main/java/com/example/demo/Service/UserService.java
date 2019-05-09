package com.example.demo.Service;

import com.example.demo.Entity.Utente;

import java.util.List;

public interface UserService {
    public void save(Utente u);

    public Utente getToken(String randomUUID);

    public Utente getUserById(String username);

    public Utente getTokenForRecovery(String randomUUID);

    public List<Utente> getAllUsers();
}
