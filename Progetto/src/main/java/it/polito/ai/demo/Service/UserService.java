package it.polito.ai.demo.Service;

import it.polito.ai.demo.Entity.Utente;

import java.util.List;

public interface UserService {
    public void save(Utente u);

    public Utente getToken(String randomUUID);

    public Utente getUserById(String username);

    public Utente getTokenForRecovery(String randomUUID);

    public List<Utente> getAllUsers();
}
