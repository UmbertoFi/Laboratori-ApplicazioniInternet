package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.idTurno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, idTurno> {


  @Query("select t from Turno t where t.consolidato = true and t.id.utente=?1")
  List<Turno> getTurniProx(Utente user);
}
