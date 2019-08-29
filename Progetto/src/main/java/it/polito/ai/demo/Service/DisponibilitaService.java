package it.polito.ai.demo.Service;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.Disponibilita;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilitaService {
  public void save(Disponibilita d);

  public List<DisponibilitaGetDTO> getDisponibilitaByCorsa(int id);

  public List<Disponibilita> getDisponibilitaByData(LocalDate data);

  public void deleteOne(Disponibilita d);
}
