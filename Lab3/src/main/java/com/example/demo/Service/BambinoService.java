package com.example.demo.Service;

import com.example.demo.Entity.Bambino;

public interface BambinoService {
    public String getNome(int id_bambino);

    public Iterable<Bambino> getBambini();

    public String getCognome(int id_bambino);
}
