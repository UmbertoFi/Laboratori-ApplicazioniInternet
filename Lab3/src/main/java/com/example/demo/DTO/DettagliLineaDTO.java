package com.example.demo.DTO;

import lombok.*;


@Builder(toBuilder = true)
@Setter
@Getter
public class DettagliLineaDTO {
    private int id;
    private String nome;
    private String ora;
}
