package it.polito.ai.lab2.demo;

import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Repository.PrenotazioneRepository;
import it.polito.ai.lab2.demo.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PrenotazioneServiceImpl implements PrenotazioneService {


    @Autowired
    PrenotazioneRepository repo;

    public void addPrenotazione(Prenotazione p){
        repo.save(p);
    }
}
