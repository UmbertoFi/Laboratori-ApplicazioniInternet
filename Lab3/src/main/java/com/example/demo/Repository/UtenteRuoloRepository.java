package com.example.demo.Repository;

import com.example.demo.Entity.Utente;
import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRuoloRepository extends CrudRepository<UtenteRuolo, idRuolo> {

    @Query("select ur from UtenteRuolo ur where ur.ruolo = 'system-admin'")
    Optional<UtenteRuolo> findSystemAdmin();

}
