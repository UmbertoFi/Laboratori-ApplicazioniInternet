package it.polito.ai.lab2.demo.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@JsonDeserialize(using = DeserializationL.class)
public class Linea {
    @Id
    private int id;
    private String nome;
    private String amministratore;
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private Set<Fermata> fermate = new HashSet<Fermata>();


}