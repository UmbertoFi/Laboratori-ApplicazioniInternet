package com.example.demo.Service;


import com.example.demo.DTO.LineaDTO;
import com.example.demo.Entity.Linea;


public interface LineaService {

    public void save(LineaDTO l);

    public Iterable<Linea> getLines();
}