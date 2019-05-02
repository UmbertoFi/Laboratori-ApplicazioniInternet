package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.Linea;
import com.example.demo.Repository.LineaRepository;
import com.example.demo.Service.LineaService;
import com.example.demo.DTO.LineaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class LineaServiceImpl implements LineaService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LineaRepository lineaRepository;

    public void save(LineaDTO l) {
        Linea linea = l.convertToEntity();
        em.persist(linea);
    }

    public Iterable<Linea> getLines(){
        return lineaRepository.findAll();
    }

}
