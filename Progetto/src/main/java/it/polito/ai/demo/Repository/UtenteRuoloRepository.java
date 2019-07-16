package it.polito.ai.demo.Repository;

import it.polito.ai.demo.Entity.Linea;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.UtenteRuolo;
import it.polito.ai.demo.Entity.idRuolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRuoloRepository extends CrudRepository<UtenteRuolo, idRuolo> {

    @Query("select ur from UtenteRuolo ur where ur.id.ruolo = 'system-admin'")
    Optional<UtenteRuolo> findSystemAdmin();

    @Query("select ur from UtenteRuolo ur where ur.id.utente = ?1")
    Iterable<UtenteRuolo> findByUtente(Utente utente);

    //@Query("select ur from UtenteRuolo ur where ur.nomeLinea = ?1 and ur.id.ruolo = 'admin'")
    //Iterable<UtenteRuolo> findAdminByLinea(String linea);
}
