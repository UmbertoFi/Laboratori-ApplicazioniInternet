package com.example.demo.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
/*@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)*/
@Setter
@Getter
public class DettagliLineaPersoneDTO {
    private int id;
    private String nome;
    private List<String> persone = new ArrayList<>();
}
