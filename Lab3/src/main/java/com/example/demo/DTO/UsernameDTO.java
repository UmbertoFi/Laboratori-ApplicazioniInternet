package com.example.demo.DTO;


import lombok.*;

import javax.validation.constraints.Email;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter

public class UsernameDTO {

    private String username;
}
