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
    private Boolean accompagnatore;

    public void increment() {
        this.count++;
    }

  @Override
  public String toString() {
    return "NotificaTurnoDTO{" +
      "tipo=" + tipo +
      ", msg='" + msg + '\'' +
      ", data='" + data + '\'' +
      ", verso='" + verso + '\'' +
      ", utente='" + utente + '\'' +
      ", linea='" + linea + '\'' +
      '}';
  }
}
