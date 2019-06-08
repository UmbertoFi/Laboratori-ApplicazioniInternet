package com.example.demo.Entity;

import com.example.demo.DTO.CorsaSDTO;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Corsa {

    @EmbeddedId
    private idCorsa id;

    public CorsaSDTO convertToDTO(){

        CorsaSDTO c=CorsaSDTO.builder()
                .data(id.getData().toString())
                .nome_linea(id.getLinea().getNome())
                .verso(id.getVerso())
                .build();

        return c;
    }
}
