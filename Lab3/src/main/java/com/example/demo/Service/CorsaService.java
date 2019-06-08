package com.example.demo.Service;

import com.example.demo.Entity.Corsa;

public interface CorsaService {

    public void save(Corsa c);

    public Iterable<Corsa> getCorse();

    public Iterable<Corsa> getCorseByIdLinea(Integer n_linea);
}
