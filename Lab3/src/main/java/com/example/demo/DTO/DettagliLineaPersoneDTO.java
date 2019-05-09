package com.example.demo.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
public class DettagliLineaPersoneDTO {
    private int id;
    private String nome;
    private List<String> persone = new ArrayList<>();
}
