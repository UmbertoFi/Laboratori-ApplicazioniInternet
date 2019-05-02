package com.example.demo.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class Utente {

    @Id
    @Email
    private String UserName;

    private String Password;

    private String Status;

}
