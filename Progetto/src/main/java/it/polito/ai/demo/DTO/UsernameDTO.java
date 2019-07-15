package it.polito.ai.demo.DTO;


import lombok.*;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsernameDTO implements Serializable {

    private String username;
}
