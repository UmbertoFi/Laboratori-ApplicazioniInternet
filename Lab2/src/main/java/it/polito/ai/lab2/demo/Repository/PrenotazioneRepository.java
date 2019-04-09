package it.polito.ai.lab2.demo.Repository;

import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, idPrenotazione> {

}