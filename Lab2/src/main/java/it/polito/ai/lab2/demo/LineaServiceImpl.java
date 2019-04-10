package it.polito.ai.lab2.demo;

import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Repository.LineaRepository;
import it.polito.ai.lab2.demo.Service.LineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineaServiceImpl implements LineaService {

    @Autowired
    LineaRepository repo;

    public void addLinea(Linea l){
        repo.save(l);
    }

    public static class PrenotazioneServiceImpl {
    }
}
