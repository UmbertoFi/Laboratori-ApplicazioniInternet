package it.polito.ai.lab2.demo.Repository;

import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;
import org.springframework.data.repository.CrudRepository;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, idPrenotazione> {

}