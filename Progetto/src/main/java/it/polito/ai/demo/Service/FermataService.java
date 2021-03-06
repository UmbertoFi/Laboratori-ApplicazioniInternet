package it.polito.ai.demo.Service;


import it.polito.ai.demo.Entity.Fermata;
import java.util.List;
import java.util.Optional;

public interface FermataService {

    public void save(Fermata f);

    public Iterable<Fermata> getFermate();

    public List<Fermata> getFermateList(String nomeLinea);

    public Optional<Fermata> getFermata(int parseInt);
}
