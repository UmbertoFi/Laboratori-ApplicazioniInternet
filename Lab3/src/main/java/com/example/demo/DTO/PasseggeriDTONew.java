package com.example.demo.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter
@Getter
public class PasseggeriDTONew {
    @Builder.Default
    private List<DettagliLineaPersoneDTONew> fermateA = new ArrayList<>();
    @Builder.Default
    private List<DettagliLineaPersoneDTONew> fermateR = new ArrayList<>();

}
