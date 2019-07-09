package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DisponibilitaRepository extends CrudRepository<Disponibilita, idDisponibilita> {
}
