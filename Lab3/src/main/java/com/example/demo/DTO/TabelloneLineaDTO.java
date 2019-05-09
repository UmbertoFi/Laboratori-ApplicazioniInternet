package com.example.demo.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter
@Getter
public class TabelloneLineaDTO {
    @Builder.Default
    private List<DettagliLineaDTO> fermateA = new ArrayList<>();
    @Builder.Default
    private List<DettagliLineaDTO> fermateR = new ArrayList<>();
}
