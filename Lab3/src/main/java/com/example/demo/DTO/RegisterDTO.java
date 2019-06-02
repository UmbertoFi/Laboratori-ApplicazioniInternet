package com.example.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
public class RegisterDTO {

    private String email;

    private String password;

    private String confirmPassword;


}
