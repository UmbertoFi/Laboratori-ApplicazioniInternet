package it.polito.ai.demo.Repository;


import it.polito.ai.demo.Entity.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Utente, String> {

    @Query("select u from Utente u where u.token = ?1 and u.enabled=false")
    Optional<Utente> findByToken(String randomUUID);

    @Query("select u from Utente u where u.token = ?1") //con la c minuscola
    Optional<Utente> findByTokenForRecovery(String randomUUID);

}
