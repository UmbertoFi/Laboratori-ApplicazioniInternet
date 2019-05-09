package com.example.demo.Service;

import com.example.demo.Entity.UtenteRuolo;

import java.util.List;

public interface UtenteRuoloService {
    public UtenteRuolo getUtenteRuolo(String username, String linea);

    public void save(UtenteRuolo ur);

    public boolean getByRuoloSystemAdmin();

    public List<UtenteRuolo> getAll();

    public void deleteOne(UtenteRuolo ur);
}