package it.polito.ai.lab1.demo.ViewModels;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginVM {
    @NotNull
    @Size(min=10, max=20)
    @Email
    private String mail;
    @NotNull
    @Size(min=7, max=20)
    private String pass1;
}
