package com.example.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
/*@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)*/
@Setter
@Getter
public class RegisterDTO {

    private String username;

    private String password;

    private String password2;


}
