package it.polito.ai.lab1.demo.ViewModels;

import lombok.Data;

@Data
public class UserRecord {

    private String nome;
    private String cognome;
    private String pass;

    public UserRecord(String nome, String cognome, String pass) {
        this.nome = nome;
        this.cognome = cognome;
        this.pass = pass;
    }
}