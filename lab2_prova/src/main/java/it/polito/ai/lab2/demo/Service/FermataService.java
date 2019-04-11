package it.polito.ai.lab2.demo.Service;


import it.polito.ai.lab2.demo.Entity.Fermata;

public interface FermataService {

    public void save(Fermata f);

    Iterable<Fermata> getFermate();
}
