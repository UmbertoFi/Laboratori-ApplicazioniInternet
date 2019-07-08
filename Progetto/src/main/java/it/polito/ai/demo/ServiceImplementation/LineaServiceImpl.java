package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.Entity.Linea;
import it.polito.ai.demo.Repository.LineaRepository;
import it.polito.ai.demo.Service.LineaService;
import it.polito.ai.demo.DTO.LineaDTO;

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

    public Iterable<Linea> getLines() {
        return lineaRepository.findAll();
    }

    public Linea getLinea(String line){
        return lineaRepository.findByNome(line);
    }

}
