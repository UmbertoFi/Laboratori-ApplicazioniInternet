package it.polito.ai.demo.Service;


import it.polito.ai.demo.Entity.Prenotazione;
import it.polito.ai.demo.Entity.idPrenotazione;

import java.util.Optional;

public interface PrenotazioneService {

    public void save(Prenotazione p);

    public Iterable<Prenotazione> getPrenotazioni();

    public Optional<Prenotazione> getPrenotazione(idPrenotazione ip);

    public void deleteOne(Prenotazione prenotazione);
}

