package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    public void save(Utente u){
        em.persist(u);
    }


    public Utente getToken(String randomUUID){
        Optional<Utente> u=userRepository.findByToken(randomUUID);

        if(u.isPresent()==true){
            return u.get();
        }
            return null;
    }

    public Utente getTokenForRecovery(String randomUUID){
        Optional<Utente> u=userRepository.findByTokenAndExpiredCredential(randomUUID);

        if(u.isPresent()==true){
            return u.get();
        }
        return null;
    }

    public Utente getUserById(String username){
        Optional<Utente> u=userRepository.findById(username);

        if(u.isPresent()==true){
            return u.get();
        }
        return null;
    }

    public List<Utente> getAllUsers(){
        Iterable<Utente> users = userRepository.findAll();

        List<Utente> usersList = new ArrayList<Utente>();

        for(Utente u : users) {
            if(u.getEnabled()==true)
            usersList.add(u);
        }

        return usersList;
    }
}
