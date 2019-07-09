package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Fermata;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Service.BambinoService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BambinoRepository extends CrudRepository<Bambino, Integer> {
    @Query("select b from Bambino b where b.genitore = ?1")
    Iterable<Bambino> getBambiniByUsername(Utente utente);
}
