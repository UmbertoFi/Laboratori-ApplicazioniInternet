package com.example.demo.Entity;

import com.example.demo.DTO.DettagliLineaDTO;
import com.example.demo.DTO.DettagliLineaPersoneDTO;
import com.example.demo.DTO.FermataDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Bambino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    @ManyToOne
    @JoinColumn(name = "id_genitore")
    private Utente genitore;
}
