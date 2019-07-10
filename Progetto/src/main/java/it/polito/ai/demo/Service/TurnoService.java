package it.polito.ai.demo.Service;

import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.idTurno;

import java.util.Optional;

public interface TurnoService {
    public void save(Turno t);

    public Optional<Turno> getTurnoById(idTurno id);
}
