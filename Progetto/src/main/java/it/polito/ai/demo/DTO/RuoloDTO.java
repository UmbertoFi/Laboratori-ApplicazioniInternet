package it.polito.ai.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RuoloDTO {

    private String ruolo;
}
