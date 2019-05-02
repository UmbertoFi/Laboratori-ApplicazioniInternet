package com.example.demo;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@RestController
@RequestMapping(path = "demo")
public class UserController {



    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailService email;

    @PostMapping(path = "/register")
    public @ResponseBody
    String postNuovoUser(@RequestBody RegisterDTO registerDTO) {

    if(userRepository.findById(registerDTO.getUserName()).isPresent()){
       return "utente già esistente";
    }

    if(registerDTO.getPassword().compareTo(registerDTO.getPassword2())!=0){
        return "password diverse";

    }

    Utente u= Utente.builder()
            .UserName(registerDTO.getUserName())
            .Password(registerDTO.getPassword())
            .Status("waiting")
            .build();

    System.out.println("ci sono");
    userService.save(u);

    email.sendSimpleMessage(registerDTO.getUserName(), "Convocazione partita del cuore-Città di Torino", "Gentilissimo, siccome sbagli tutti i cross sei stato convocato per la partita del cuore! confermi di essere malato?  GREIT");

    return "ok";
    }

}
