package com.example.demo.Service;

import com.example.demo.Entity.UtenteRuolo;

public interface UtenteRuoloService {
    public UtenteRuolo getUtenteRuolo(String username, String linea);

    public void save(UtenteRuolo ur);
}