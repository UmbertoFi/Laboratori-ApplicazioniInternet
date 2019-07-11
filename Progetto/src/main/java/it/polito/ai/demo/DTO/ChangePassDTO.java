package it.polito.ai.demo.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter
public class ChangePassDTO {

    private String password0;

    private String password1;

    private String password2;


}
