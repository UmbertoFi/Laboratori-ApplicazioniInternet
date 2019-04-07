package it.polito.ai.lab1.demo.ViewModels;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationVM {

    @NotNull
    @Size(min=1, max=20)
    private String nome;
    @NotNull
    @Size(min=1, max=20)
    private String cognome;
    @NotNull
    @Size(min=10, max=20)
    @Email
    private String mail;
    @NotNull
    @Size(min=7, max=20)
    private String pass1;
    @NotNull
    @Size(min=7, max=20)
    private String pass2;
    private boolean privacy;

}
