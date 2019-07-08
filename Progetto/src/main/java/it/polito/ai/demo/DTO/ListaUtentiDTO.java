package it.polito.ai.demo.DTO;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ListaUtentiDTO {
    private List<UtenteDTO> utenti;
}
