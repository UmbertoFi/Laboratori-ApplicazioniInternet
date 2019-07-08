package it.polito.ai.demo.Entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class idCorsa implements Serializable {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
    private String verso;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;
}
