package com.example.demo.Service;


import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.idPrenotazione;

import java.util.Optional;

public interface PrenotazioneService {

    public void save(Prenotazione p);

    public Iterable<Prenotazione> getPrenotazioni();

    public Optional<Prenotazione> getPrenotazione(idPrenotazione ip);

    public void deleteOne(Prenotazione prenotazione);
}

