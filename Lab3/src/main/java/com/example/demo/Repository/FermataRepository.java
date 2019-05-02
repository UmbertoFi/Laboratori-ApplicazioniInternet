package com.example.demo.Repository;

import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Linea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FermataRepository extends CrudRepository<Fermata, Integer> {

    @Query("select f from Fermata f where f.linea = ?1 order by f.n_seq")
    List<Fermata> findByLinea(Linea linea);

}