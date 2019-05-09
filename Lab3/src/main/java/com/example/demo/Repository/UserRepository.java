package com.example.demo.Repository;


import com.example.demo.Entity.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Utente, String> {

    @Query("select u from Utente u where u.token = ?1 and u.enabled=false")
    Optional<Utente> findByToken(String randomUUID);

    @Query("select u from Utente u where u.token = ?1 and u.expiredCredential=false")
    Optional<Utente> findByTokenAndExpiredCredential(String randomUUID);
}
