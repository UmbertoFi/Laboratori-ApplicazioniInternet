package it.polito.ai.lab2.demo;

import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Repository.FermataRepository;
import it.polito.ai.lab2.demo.Service.FermataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class FermataServiceImpl implements FermataService {

    @Autowired
    FermataRepository repo;

    public void addFermate(Linea l) {
        Set<Fermata> fermate = l.getFermate();
        for (Fermata f : fermate) {
            repo.save(f);
        }
    }
}
