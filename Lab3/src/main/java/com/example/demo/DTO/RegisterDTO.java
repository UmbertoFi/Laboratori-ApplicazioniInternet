package com.example.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
public class RegisterDTO {

    private String username;

    private String password;

    private String password2;


}
