package it.polito.ai.lab2.demo.DTO;

import lombok.*;

@Builder(toBuilder = true)
/*@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)*/
@Setter
@Getter
public class NomeLineaDTO {
    private String nome;
}
