package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.idTurno;
import it.polito.ai.demo.Repository.TurnoRepository;
import it.polito.ai.demo.Service.DisponibilitaService;
import it.polito.ai.demo.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    TurnoRepository turnoRepository;

    public void save(Turno t) {
        em.persist(t);
    }

    public Optional<Turno> getTurnoById(idTurno id) {
        return turnoRepository.findById(id); }
}
