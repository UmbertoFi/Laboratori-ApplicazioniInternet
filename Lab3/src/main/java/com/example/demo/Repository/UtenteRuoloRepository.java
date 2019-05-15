package com.example.demo.Repository;

import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRuoloRepository extends CrudRepository<UtenteRuolo, idRuolo> {

    @Query("select ur from UtenteRuolo ur where ur.ruolo = 'system-admin'")
    Optional<UtenteRuolo> findSystemAdmin();

    @Query("select ur from UtenteRuolo ur where ur.id.username = ?1")
    Iterable<UtenteRuolo> findByUsername(String username);



}
