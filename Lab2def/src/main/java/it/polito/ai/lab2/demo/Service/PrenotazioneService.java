package it.polito.ai.lab2.demo.Service;


import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;

import java.util.Optional;

public interface PrenotazioneService {

    public void save(Prenotazione p);
    public Iterable<Prenotazione> getPrenotazioni();

    public  Optional<Prenotazione> getPrenotazione(idPrenotazione ip);

    public void deleteOne(Prenotazione prenotazione);
}

