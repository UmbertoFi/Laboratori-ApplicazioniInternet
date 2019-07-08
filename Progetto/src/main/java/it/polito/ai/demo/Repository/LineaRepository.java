package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Linea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaRepository extends CrudRepository<Linea, Integer> {

    @Query("select l from Linea l where l.nome = ?1")
    Linea findByNome(String nome);
}