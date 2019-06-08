package com.example.demo.DTO;

import com.example.demo.Entity.Corsa;
import com.example.demo.Entity.idCorsa;
import com.example.demo.Repository.LineaRepository;
import com.example.demo.Service.CorsaService;
import lombok.*;

import java.time.LocalDate;


@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class CorsaDTO {
    private String data;
    private int id_linea;
    private String verso;

    public Corsa convertToEntity(LineaRepository lr) {

        String[] dataPieces = this.data.split("-");
        LocalDate n_data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));


        idCorsa id= idCorsa.builder()
                .data(n_data)
                .linea(lr.findById(id_linea).get())
                .verso(verso)
                .build();

        Corsa c=Corsa.builder()
                .id(id)
                .build();
        return c;
    }
}
