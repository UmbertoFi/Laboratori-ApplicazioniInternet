package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.Entity.Linea;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.UtenteRuolo;
import it.polito.ai.demo.Entity.idRuolo;
import it.polito.ai.demo.Repository.UserRepository;
import it.polito.ai.demo.Repository.UtenteRuoloRepository;
import it.polito.ai.demo.Service.UtenteRuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
@Transactional
public class UtenteRuoloServiceImpl implements UtenteRuoloService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UtenteRuoloRepository utenteRuoloRepository;
    @Autowired
    UserRepository userRepository;

    public void save(UtenteRuolo ur) {
        em.persist(ur);
    }


    public List<UtenteRuolo> getRuoli(String username) {

        //idRuolo id = idRuolo.builder().Username(username).NomeLinea(linea).build();
        Optional<Utente> utente = userRepository.findById(username);
        if(utente.isPresent()) {
            Iterable<UtenteRuolo> ur = utenteRuoloRepository.findByUtente(utente.get());
            List<UtenteRuolo> listUr = new ArrayList<>();
            for (UtenteRuolo u : ur)
                listUr.add(u);
            return listUr;
        }
        return null;
    }


    public List<Utente> getAdminByLinea(String linea) {

        List<Utente> res=new ArrayList<>();
        Iterable<UtenteRuolo>ur= utenteRuoloRepository.findAdminByLinea(linea);
        if(ur.iterator().hasNext()) {
            for (UtenteRuolo x : ur) {
                res.add(x.getId().getUtente());
            }
            return res;
        }
        return null;
    }

    public List<Utente> getAccompagnatoreByLinea(String linea) {

        List<Utente> res=new ArrayList<>();
        Iterable<UtenteRuolo>ur= utenteRuoloRepository.findAccompagnatoreByLinea(linea);
        if(ur.iterator().hasNext()) {
            for (UtenteRuolo x : ur) {
                res.add(x.getId().getUtente());
            }
            return res;
        }
        return null;
    }

    public UtenteRuolo getUtenteRuolo(String username, String linea, String ruolo) {

        Optional<Utente> utente = userRepository.findById(username);
        if(utente.isPresent()) {
            idRuolo id = idRuolo.builder().utente(utente.get()).ruolo(ruolo).NomeLinea(linea).build();
            Optional<UtenteRuolo> ur = utenteRuoloRepository.findById(id);
            if (ur.isPresent()) {
                return ur.get();
            }
        }
        return null;
    }

    public boolean getByRuoloSystemAdmin() {
        if (utenteRuoloRepository.findSystemAdmin().isPresent())
            return true;
        else
            return false;
    }

    public List<UtenteRuolo> getAll() {
        List<UtenteRuolo> ruoli = new ArrayList<UtenteRuolo>();

        Iterable<UtenteRuolo> roles = utenteRuoloRepository.findAll();

        for (UtenteRuolo r : roles)
            ruoli.add(r);

        return ruoli;
    }

    public void deleteOne(UtenteRuolo ur) {
        utenteRuoloRepository.delete(ur);
    }


}

