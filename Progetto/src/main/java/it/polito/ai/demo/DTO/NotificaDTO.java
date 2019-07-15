package it.polito.ai.demo.DTO;

import lombok.*;

import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class NotificaDTO implements Serializable {
    private int count;
    private String msg;

    public void increment() {
        this.count++;
    }

}
