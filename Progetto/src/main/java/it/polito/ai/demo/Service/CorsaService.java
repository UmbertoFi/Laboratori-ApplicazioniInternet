package it.polito.ai.demo.Service;

import it.polito.ai.demo.Entity.Corsa;
import it.polito.ai.demo.Entity.Linea;

import java.time.LocalDate;

public interface CorsaService {

    public void save(Corsa c);

    public Iterable<Corsa> getCorse();

    public Iterable<Corsa> getCorseByIdLinea(Integer n_linea);

    public Corsa getCorsa(int linea, LocalDate data, String verso);
}
