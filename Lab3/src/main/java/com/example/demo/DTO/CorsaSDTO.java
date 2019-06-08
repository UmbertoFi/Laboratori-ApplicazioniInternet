package com.example.demo.DTO;

import com.example.demo.Entity.Corsa;
import com.example.demo.Entity.idCorsa;
import com.example.demo.Repository.LineaRepository;
import lombok.*;

import java.time.LocalDate;


@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class CorsaSDTO {
    private String data;
    private String nome_linea;
    private String verso;


}
