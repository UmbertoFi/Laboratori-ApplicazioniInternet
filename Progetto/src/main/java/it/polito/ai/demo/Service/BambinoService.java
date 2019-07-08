package it.polito.ai.demo.Service;

import it.polito.ai.demo.Entity.Bambino;

public interface BambinoService {
    public String getNome(int id_bambino);

    public Iterable<Bambino> getBambini();

    public String getCognome(int id_bambino);
}
