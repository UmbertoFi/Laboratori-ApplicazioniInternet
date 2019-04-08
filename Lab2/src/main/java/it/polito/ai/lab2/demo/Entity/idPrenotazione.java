package it.polito.ai.lab2.demo.Entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class idPrenotazione implements Serializable {
    private String persona;
    private Date data;
    private String verso;

    // Scrivere hashcode, equals e toString


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}