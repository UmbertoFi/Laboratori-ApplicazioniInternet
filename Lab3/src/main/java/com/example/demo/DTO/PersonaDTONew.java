package com.example.demo.DTO;


import com.example.demo.Entity.Bambino;
import com.example.demo.Entity.Utente;
import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTONew {
    private String nome;
    private boolean selected;
}
