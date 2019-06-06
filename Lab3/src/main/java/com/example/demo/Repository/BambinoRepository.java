package com.example.demo.Repository;

import com.example.demo.Entity.Bambino;
import com.example.demo.Entity.Fermata;
import com.example.demo.Service.BambinoService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BambinoRepository extends CrudRepository<Bambino, Integer> {
}
