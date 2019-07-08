package it.polito.ai.demo.DTO;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ListaCorseDTO {
    @Builder.Default
    private List<CorsaDTO> corse=new ArrayList<CorsaDTO>();
}
