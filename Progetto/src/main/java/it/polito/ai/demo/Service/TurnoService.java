package it.polito.ai.demo.Service;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.idTurno;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TurnoService {
    public void save(Turno t);

    public Optional<Turno> getTurnoById(idTurno id);

  public List<DisponibilitaGetDTO> getProxTurni(Utente user);

  public void deleteTurno(idTurno id);

  public List<Turno> getTurniByData(LocalDate data);

  public void deleteOne(Turno t);
}
