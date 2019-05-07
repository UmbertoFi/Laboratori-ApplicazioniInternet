package com.example.demo.Repository;

import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRuoloRepository extends CrudRepository<UtenteRuolo, idRuolo> {


}
