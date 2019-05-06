package com.example.demo;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.Utente;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(path = "demo")
public class UserController {



    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailService email;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;


    /*
    @PostMapping(path = "/login")
    public @ResponseBody
    String postLogin(@RequestBody LoginDTO loginDTO){


        if(userRepository.findById(loginDTO.getUserName()).isPresent()){
            Utente u=userRepository.findById(loginDTO.getUserName()).get();
            if(loginDTO.getPassword().compareTo(u.getPassword())==0 && u.getStatus().compareTo("active")==0){
                //dovrebbe ritornare il JWT
                return "login effettuato con successo";
            }
            else
                throw new UnauthorizedException();
        } else
            throw new UnauthorizedException();
    }*/




    @PostMapping(path= "/login")
    public ResponseEntity signin(@RequestBody LoginDTO data) {

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());


            /*HttpHeaders h=new HttpHeaders();
            h.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            h.add("Authorization", "Bearer "+token);*/


            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }


    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "/register")
    public @ResponseBody
    String postNuovoUser(@RequestBody RegisterDTO registerDTO) {

    if(userRepository.findById(registerDTO.getUsername()).isPresent()){
       return "utente già esistente";
    }
    if(registerDTO.getPassword().compareTo(registerDTO.getPassword2())!=0){
        return "password diverse";

    }

    Utente u= Utente.builder()
            .UserName(registerDTO.getUsername())
            //.Password(new BCryptPasswordEncoder(11).encode(registerDTO.getPassword()))
            .Password(passwordEncoder.encode(registerDTO.getPassword()))
            .Status("active")//andrebbe "waiting"
            .build();

    System.out.println("ci sono");
    userService.save(u);

    email.sendSimpleMessage(registerDTO.getUsername(), "Convocazione partita del cuore-Città di Torino", "Gentilissimo, siccome sbagli tutti i cross sei stato convocato per la partita del cuore! confermi di essere malato?  GREIT");

    return "ok";
    }




}
