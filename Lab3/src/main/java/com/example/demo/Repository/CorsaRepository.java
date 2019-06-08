package com.example.demo.Repository;

import com.example.demo.Entity.Corsa;
import com.example.demo.Entity.idCorsa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CorsaRepository extends CrudRepository<Corsa, idCorsa> {

    @Query("select c from Corsa c where c.id.linea.id = ?1")
    Iterable<Corsa> getCorsaByIdLinea(int id);
}
