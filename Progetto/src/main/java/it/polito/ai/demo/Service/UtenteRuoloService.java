package it.polito.ai.demo.Service;

import it.polito.ai.demo.Entity.UtenteRuolo;

import java.util.List;

public interface UtenteRuoloService {
    public UtenteRuolo getUtenteRuolo(String username, String linea);

    public void save(UtenteRuolo ur);

    public boolean getByRuoloSystemAdmin();

    public List<UtenteRuolo> getAll();

    public void deleteOne(UtenteRuolo ur);

    public List<UtenteRuolo> getRuoli(String username);
}