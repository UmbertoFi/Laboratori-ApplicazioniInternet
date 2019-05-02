package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Linea;
import com.example.demo.Repository.FermataRepository;
import com.example.demo.Repository.LineaRepository;
import com.example.demo.Service.FermataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FermataServiceImpl implements FermataService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    FermataRepository fermataRepository;

    @Autowired
    LineaRepository lineaRepository;

    public void save(Fermata f) {
        em.persist(f);
    }

    public Iterable<Fermata> getFermate(){

        return fermataRepository.findAll();
    }

    public List<Fermata> getFermateList(String nomeLinea){

        Linea l=lineaRepository.findByNome(nomeLinea);
        return fermataRepository.findByLinea(l);
    }

    public Optional<Fermata> getFermata(int parseInt){
        return fermataRepository.findById(parseInt);
    }
}
