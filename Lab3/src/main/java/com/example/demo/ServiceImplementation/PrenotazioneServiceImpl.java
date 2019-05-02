package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.idPrenotazione;
import com.example.demo.Repository.PrenotazioneRepository;
import com.example.demo.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Transactional
@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public void save(Prenotazione p){
        em.persist(p);

    }

    public Iterable<Prenotazione> getPrenotazioni(){

        return prenotazioneRepository.findAll();
    }

    public Optional<Prenotazione> getPrenotazione(idPrenotazione ip) {

        return prenotazioneRepository.findById(ip);
    }

    public void deleteOne(Prenotazione prenotazione){

        prenotazioneRepository.delete(prenotazione);
    }
}
