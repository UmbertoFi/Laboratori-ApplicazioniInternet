package it.polito.ai.demo.DTO;

import lombok.*;

import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class NotificaTurnoDTO implements Serializable {
    private int count;
    private int tipo;
    private String msg;
    private String data;
    private String verso;
    private String utente;
    private String linea;

    public void increment() {
        this.count++;
    }

}
