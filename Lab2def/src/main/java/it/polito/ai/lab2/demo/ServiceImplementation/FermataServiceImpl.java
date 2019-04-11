package it.polito.ai.lab2.demo.ServiceImplementation;

import it.polito.ai.lab2.demo.DTO.FermataDTO;
import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Repository.FermataRepository;
import it.polito.ai.lab2.demo.Service.FermataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class FermataServiceImpl implements FermataService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    FermataRepository fermataRepository;

    public void save(Fermata f) {
        em.persist(f);
    }

    public Iterable<Fermata> getFermate(){
        return fermataRepository.findAll();
    }
}
