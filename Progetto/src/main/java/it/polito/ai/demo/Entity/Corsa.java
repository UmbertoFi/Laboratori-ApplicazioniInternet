package it.polito.ai.demo.Entity;

import it.polito.ai.demo.DTO.CorsaSDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Corsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
    private String verso;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;

    @Builder.Default
    @OneToMany(mappedBy = "corsa", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    public CorsaSDTO convertToDTO(){

        CorsaSDTO c=CorsaSDTO.builder()
                .data(data.toString())
                .nome_linea(linea.getNome())
                .verso(verso)
                .build();

        return c;
    }
}
