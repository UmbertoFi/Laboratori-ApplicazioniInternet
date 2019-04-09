package it.polito.ai.lab2.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Embeddable
@Data
public class idPrenotazione implements Serializable {
    private String persona;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
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