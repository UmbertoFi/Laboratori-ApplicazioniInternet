package com.example.demo.DTO;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ListaPrenotatiDTO {
  private List<PrenotatoDTO> prenotazioni;
}
