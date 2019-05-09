package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RecoveryVM {

    @NotNull
    @Size(min=7, max=20)
    private String pass1;
    @NotNull
    @Size(min=7, max=20)
    private String pass2;


}
