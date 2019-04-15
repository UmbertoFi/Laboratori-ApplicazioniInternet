package it.polito.ai.lab2.demo.DTO;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class DettagliLineaDTO {
    private int id;
    private String nome;
    private String ora;
}
