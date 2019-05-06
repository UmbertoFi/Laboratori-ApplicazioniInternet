package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}