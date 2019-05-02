package com.example.demo.Repository;


import com.example.demo.Entity.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<Utente, String> {
}
