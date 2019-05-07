package com.example.demo.ServiceImplementation;

import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import com.example.demo.Repository.UtenteRuoloRepository;
import com.example.demo.Service.UtenteRuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Service
@Transactional
    public class UtenteRuoloServiceImpl implements UtenteRuoloService {

        @PersistenceContext
        private EntityManager em;

        @Autowired
        UtenteRuoloRepository utenteRuoloRepository;

        public void save(UtenteRuolo ur){
            em.persist(ur);
        }

        public UtenteRuolo getUtenteRuolo(String username, String linea){

            idRuolo id=idRuolo.builder().Username(username).NomeLinea(linea).build();
            Optional<UtenteRuolo> ur=utenteRuoloRepository.findById(id);
            if(ur.isPresent()){
                return ur.get();
            }
            return null;
        }
    }

