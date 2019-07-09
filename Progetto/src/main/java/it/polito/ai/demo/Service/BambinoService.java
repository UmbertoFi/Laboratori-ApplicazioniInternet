package it.polito.ai.demo.Service;

import it.polito.ai.demo.DTO.BambinoDTO;
import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Utente;

import java.util.List;
import java.util.Optional;

public interface BambinoService {
    public String getNome(int id_bambino);

    public Iterable<Bambino> getBambini();

    public String getCognome(int id_bambino);

    public List<BambinoDTO> getFigli(Utente utente);

    public void save(Bambino b);

    public Optional<Bambino> getBambinoByAll(String nome, String cognome, Utente utente);
}
