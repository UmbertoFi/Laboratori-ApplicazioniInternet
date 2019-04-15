package it.polito.ai.lab2.demo.Repository;

import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, idPrenotazione> {

}