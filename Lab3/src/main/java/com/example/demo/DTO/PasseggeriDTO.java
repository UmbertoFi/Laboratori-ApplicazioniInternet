package com.example.demo.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter
@Getter
public class PasseggeriDTO {
    @Builder.Default
    private List<DettagliLineaPersoneDTO> fermateA = new ArrayList<>();
    @Builder.Default
    private List<DettagliLineaPersoneDTO> fermateR = new ArrayList<>();

}
