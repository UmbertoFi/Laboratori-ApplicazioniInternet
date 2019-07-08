package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Corsa;
import it.polito.ai.demo.Entity.idCorsa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface CorsaRepository extends CrudRepository<Corsa, idCorsa> {

    @Query("select c from Corsa c where c.id.linea.id = ?1")
    Iterable<Corsa> getCorsaByIdLinea(int id);

    @Query("select c from Corsa c where c.linea.id = ?1 and c.data = ?2 and c.verso = ?3")
    Corsa getCorsaByAll(int id, LocalDate n_data, String verso);

}
