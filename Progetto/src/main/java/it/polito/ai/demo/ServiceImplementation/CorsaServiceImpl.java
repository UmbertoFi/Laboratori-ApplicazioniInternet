package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.Entity.Corsa;
import it.polito.ai.demo.Repository.CorsaRepository;
import it.polito.ai.demo.Service.CorsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class CorsaServiceImpl implements CorsaService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    CorsaRepository corsaRepository;

    public void save(Corsa c) {
        em.persist(c);
    }

    public Iterable<Corsa> getCorse(){
        return corsaRepository.findAll();
    }

    public Iterable<Corsa> getCorseByIdLinea(Integer n_linea){
        return corsaRepository.getCorsaByIdLinea(n_linea);
    }
}