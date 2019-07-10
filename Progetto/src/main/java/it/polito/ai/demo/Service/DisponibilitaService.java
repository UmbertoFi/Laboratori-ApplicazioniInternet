package it.polito.ai.demo.Service;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.Disponibilita;

import java.util.List;

public interface DisponibilitaService {
    public void save(Disponibilita d);


    public List<DisponibilitaGetDTO> getDisponibilitaByCorsa(int id);
}
