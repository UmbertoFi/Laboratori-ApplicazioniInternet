package com.example.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
public class DettagliLineaPersoneDTONew {
    private int id;
    private String nome;
    private String ora;
    private List<PersonaDTONew> persone = new ArrayList<>();
}
