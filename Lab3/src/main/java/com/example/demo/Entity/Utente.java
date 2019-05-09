package com.example.demo.Entity;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String token;
    private Date expiredToken;

    private Boolean expiredAccount;

    private Boolean locked;

    private Boolean expiredCredential;

    private Boolean enabled;

    public List<String> getRoles() {
        List<String> ruoli=new ArrayList<>();
        ruoli.add("User");
        return ruoli;
    }
}
