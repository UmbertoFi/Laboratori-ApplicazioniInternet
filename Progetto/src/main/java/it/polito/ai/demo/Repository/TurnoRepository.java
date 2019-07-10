package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.idTurno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, idTurno> {

}