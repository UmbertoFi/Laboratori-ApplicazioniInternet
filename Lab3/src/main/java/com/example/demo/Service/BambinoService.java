package com.example.demo.Service;

import com.example.demo.Entity.Bambino;

public interface BambinoService {
    String getNome(int id_bambino);

    public Iterable<Bambino> getBambini();
}
