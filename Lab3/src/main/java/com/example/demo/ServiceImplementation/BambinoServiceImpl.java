package com.example.demo.ServiceImplementation;

import com.example.demo.DTO.LineaDTO;
import com.example.demo.Entity.Bambino;
import com.example.demo.Entity.Linea;
import com.example.demo.Repository.BambinoRepository;
import com.example.demo.Repository.LineaRepository;
import com.example.demo.Service.BambinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@Transactional
public class BambinoServiceImpl implements BambinoService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BambinoRepository bambinoRepository;

    public void save() {
        /* Bambino bambino = l.convertToEntity();
        em.persist(linea); */
    }

    /* public Iterable<Bambino> getLines() {
        return lineaRepository.findAll();
    } */

    public String getNome(int id_bambino){
        Optional<Bambino> bambino = bambinoRepository.findById(id_bambino);
        if(bambino.isPresent()){
            return bambino.get().getNome();
        }
            return null;
    }

    public String getCognome(int id_bambino){
        Optional<Bambino> bambino = bambinoRepository.findById(id_bambino);
        if(bambino.isPresent()){
            return bambino.get().getCognome();
        }
        return null;
    }

    public Iterable<Bambino> getBambini(){
        return bambinoRepository.findAll();
    }

}
