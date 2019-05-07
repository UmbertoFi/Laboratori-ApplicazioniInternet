package com.example.demo.Service;

import com.example.demo.Entity.Utente;

public interface UserService {
    public void save(Utente u);

    public Utente getToken(String randomUUID);

    public Utente getUserById(String username);
}
