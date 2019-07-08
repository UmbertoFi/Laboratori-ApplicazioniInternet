package it.polito.ai.demo.Entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class idPrenotazione implements Serializable {
    private int id_bambino;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
    private String verso;

    @Override
    public String toString() {

        return id_bambino + '_' + data.getYear() + "-" + data.getMonthValue() + "-" + data.getDayOfMonth() + '_' + verso;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getLocalData() {
        return this.data.getYear()+"-"+this.data.getMonth()+"-"+this.data.getDayOfMonth();
    }
}