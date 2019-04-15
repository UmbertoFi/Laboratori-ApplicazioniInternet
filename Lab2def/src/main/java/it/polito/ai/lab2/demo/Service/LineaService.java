package it.polito.ai.lab2.demo.Service;



import it.polito.ai.lab2.demo.DTO.LineaDTO;
import it.polito.ai.lab2.demo.Entity.Linea;


public interface LineaService {

    public void save(LineaDTO l);

    Iterable<Linea> getLines();
}