package it.polito.ai.demo.DTO;

import lombok.*;

import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class TurnoDTO implements Serializable {
    private String data;
    private String verso;
    private String utente;
}
