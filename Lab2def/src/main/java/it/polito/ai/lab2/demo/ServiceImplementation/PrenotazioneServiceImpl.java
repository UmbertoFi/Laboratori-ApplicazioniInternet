package it.polito.ai.lab2.demo.ServiceImplementation;

import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Repository.PrenotazioneRepository;
import it.polito.ai.lab2.demo.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    PrenotazioneRepository prenotazioneRepositoryRepository;

    public void save(Prenotazione p){
        em.persist(p);

    }
}
