package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.Entity.Disponibilita;
import it.polito.ai.demo.Service.DisponibilitaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class DisponibilitaServiceImpl implements DisponibilitaService {

    @PersistenceContext
    private EntityManager em;

    public void save(Disponibilita d){ em.persist(d);}
}
