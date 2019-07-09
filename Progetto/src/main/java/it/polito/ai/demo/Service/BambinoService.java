package it.polito.ai.demo.Service;

import it.polito.ai.demo.DTO.BambinoDTO;
import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Utente;

import java.util.List;

public interface BambinoService {
    public String getNome(int id_bambino);

    public Iterable<Bambino> getBambini();

    public String getCognome(int id_bambino);

    public List<BambinoDTO> getFigli(Utente utente);
}
