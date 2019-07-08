package it.polito.ai.demo.Service;


import it.polito.ai.demo.DTO.LineaDTO;
import it.polito.ai.demo.Entity.Linea;


public interface LineaService {

    public void save(LineaDTO l);

    public Iterable<Linea> getLines();

    public Linea getLinea(String line);
}