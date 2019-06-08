package com.example.demo.DTO;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ListaCorseDTO {

    private List<CorsaDTO> corse=new ArrayList<CorsaDTO>();
}
