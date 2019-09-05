package it.polito.ai.demo.Repository;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface DisponibilitaRepository extends CrudRepository<Disponibilita, idDisponibilita> {

  @Query("select d from Disponibilita d where d.id.corsa.id = ?1")
  List<Disponibilita> findByCorsa(int id);

  @Query("select d from Disponibilita d where d.id.corsa.data <= ?1")
  List<Disponibilita> findByData(LocalDate data);

  @Query("select d from Disponibilita d where d.id.utente = ?1 and d.id.corsa.data = ?2 and d.id.corsa.verso = ?3")
  List<Disponibilita> findByUserDataVerso(Utente utente, LocalDate data, String verso);
}
