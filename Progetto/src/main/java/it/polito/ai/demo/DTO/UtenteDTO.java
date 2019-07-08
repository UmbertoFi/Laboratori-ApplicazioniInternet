package it.polito.ai.demo.DTO;

import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Fermata;
import it.polito.ai.demo.Entity.Utente;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class UtenteDTO {

    private String username;
    private String password;
    private String enabled;
    private String expired_account;
    private String expiredcredential;
    private String locked;
    private String token;
    @Builder.Default
    private List<BambinoDTO> bambini = new ArrayList<BambinoDTO>();

    public Utente convertToEntity() {
        Date d = new Date();
        Utente u = Utente.builder()
                .UserName(this.username)
                .Password(this.password)
                .enabled(convertToBoolean(this.enabled))
                .expiredAccount(convertToBoolean(this.expired_account))
                .expiredToken(d)
                .expiredcredential(convertToBoolean(this.expiredcredential))
                .locked(convertToBoolean(this.locked))
                .token(this.token)
                .build();

        List<Bambino> bambini = new ArrayList<Bambino>();
        for (BambinoDTO b : this.bambini)
            bambini.add(b.convertToEntity(u));

        u.setBambini(bambini);

        return u;
    }

    private Boolean convertToBoolean(String value) {
        if (Integer.parseInt(value)==1)
            return true;
        else
            return false;
    }
}
