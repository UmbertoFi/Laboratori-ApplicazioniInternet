package it.polito.ai.lab2.demo.ServiceImplementation;

import it.polito.ai.lab2.demo.DTO.FermataDTO;
import it.polito.ai.lab2.demo.DTO.LineaDTO;
import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Repository.LineaRepository;
import it.polito.ai.lab2.demo.Service.LineaService;
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
    LineaRepository lineaRepository;

    public void save(LineaDTO l) {
        Linea linea = l.convertToEntity();
        em.persist(linea);

        /* List<FermataDTO> fermate = l.getFermate();
                for(FermataDTO fDTO : fermate){
                    Fermata f = fDTO.convertToEntity(linea);
                    em.persist(f);
                } */
    }

    public Iterable<Linea> getLines(){
        return lineaRepository.findAll();
    }

}
