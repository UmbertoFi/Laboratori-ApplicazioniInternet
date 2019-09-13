package it.polito.ai.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter
public class DisponibilitaGetDTO {

        private String linea;
        private String data;
        private String verso;
        private String username;

}
