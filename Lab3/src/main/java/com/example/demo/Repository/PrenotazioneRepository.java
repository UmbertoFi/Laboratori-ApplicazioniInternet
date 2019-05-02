package com.example.demo.Repository;

import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.idPrenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, idPrenotazione> {

    @Query("select l from Prenotazione l where l.id = ?1")
    Optional<Prenotazione> findById(idPrenotazione dPre);

}