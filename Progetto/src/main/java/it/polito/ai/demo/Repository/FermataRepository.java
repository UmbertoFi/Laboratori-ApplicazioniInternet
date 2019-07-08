package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Fermata;
import it.polito.ai.demo.Entity.Linea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FermataRepository extends CrudRepository<Fermata, Integer> {

    @Query("select f from Fermata f where f.linea = ?1 order by f.n_seq")
    List<Fermata> findByLinea(Linea linea);

}