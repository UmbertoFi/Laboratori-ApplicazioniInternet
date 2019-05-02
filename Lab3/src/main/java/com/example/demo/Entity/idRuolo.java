package com.example.demo.Entity;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//@Setter(value = AccessLevel.PACKAGE)
@Setter
@Getter
public class idRuolo implements Serializable {

    @Email
    private String Username;

    private String NomeLinea;

}
