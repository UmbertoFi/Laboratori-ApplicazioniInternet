package com.example.demo;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.DTO.UsernameDTO;
import com.example.demo.Entity.Utente;
import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UtenteRuoloRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UtenteRuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;

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



    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    UtenteRuoloService utenteRuoloService;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }


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


    /***
     * Lab 3 Punto 1
     * @param data
     * @return
     *
     * Funzione di Login    riceve un JSON con le credenziali di Accesso
     *                      restituisce il JWT o HttpStatus.UNAUTHORIZED
     */

    @PostMapping(path= "/login")
    public ResponseEntity signin(@RequestBody LoginDTO data) {
        String token;

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            Optional<Utente> u=users.findById(username);
            if(u.isPresent()==true){
                Utente utente=u.get();

                UtenteRuolo ruoli=utenteRuoloService.getUtenteRuolo(username, "Santa_Rita-Politecnico");


                if((utente.getEnabled()==true) && (utente.getExpiredAccount()==true) && (utente.getExpiredCredential()==true) && (utente.getLocked()==true)) {

                     token= jwtTokenProvider.createToken(username, ruoli.getRuolo());

                }
                else{
                    token=null;
                }

            }
            else{
                throw new UsernameNotFoundException("Username " + username + "not found");
            }


            //String token = jwtTokenProvider.createToken(username, this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());


            /*HttpHeaders h=new HttpHeaders();
            h.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            h.add("Authorization", "Bearer "+token);*/


            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        }
    }


    @Autowired
    PasswordEncoder passwordEncoder;


    /***
     * Lab 3 Punto 2
     * @param registerDTO
     * @return
     *
     * Funzione di registrazione, verifica la presenza di utente e in caso di nuovo utente invia una email di conferma
     */



    @PostMapping(path = "/register")
    public @ResponseBody
    String postNuovoUser(@RequestBody RegisterDTO registerDTO) {

    if(userRepository.findById(registerDTO.getUsername()).isPresent()){
       return "utente già esistente";
    }
    if(registerDTO.getPassword().compareTo(registerDTO.getPassword2())!=0){
        return "password diverse";

    }

    String UUID=generateUUID();
    Utente u= Utente.builder()
            .UserName(registerDTO.getUsername())
            //.Password(new BCryptPasswordEncoder(11).encode(registerDTO.getPassword()))
            .Password(passwordEncoder.encode(registerDTO.getPassword()))
            .token(UUID)
            .enabled(false)
            .expiredAccount(true)//logica inversa
            .expiredCredential(true)//logica inversa
            .locked(true)//logica inversa
            .build();


    userService.save(u);

        idRuolo id= idRuolo.builder()
                .Username(registerDTO.getUsername())
                .NomeLinea("Santa_Rita-Politecnico")
                .build();

    UtenteRuolo ur=UtenteRuolo.builder()
                    .id(id)
                    .Ruolo("user")
                    .build();
    utenteRuoloService.save(ur);

String body="Gentilissimo, confermi di esserti registrato al servizio?, se sì clicca il seguente link per confermare la registrazione http://localhost:8080/demo/confirm/"+UUID;
    email.sendSimpleMessage(registerDTO.getUsername(), "Benvenuto!", body);

    return "ok";
    }

    /***
     * Lab 3 Punto 3
     * @param randomUUID
     *
     * Funzione di conferma del link inviato via email in seguito alla registrazione
     */

    @GetMapping(path = "/confirm/{randomUUID}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void confirmNuovoUser(@PathVariable("randomUUID") String randomUUID) {
        Utente u=userService.getToken(randomUUID);
        if(u==null){
            throw new NotFoundException();
        }
        u.setEnabled(true);
        userRepository.save(u);
    }




    /***
     * Lab 3 Punto 4
     * @param usernameDTO
     *
     * funzione che innesca il cambio password, dopo aver verificato che l'utente esiste correttamente.
     */


    @PostMapping(path = "/recover")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void recoverPassword(@RequestBody UsernameDTO usernameDTO) {
        Utente utente=userService.getUserById(usernameDTO.getUsername());
        if(utente!=null){
            if((utente.getEnabled()==true) && (utente.getExpiredAccount()==true) && (utente.getExpiredCredential()==true) && (utente.getLocked()==true)) {

                String UUID = generateUUID();
                utente.setExpiredCredential(false);
                utente.setToken(UUID);
                String body = "Gentilissimo, confermi di aver richiesto il recupero della Password?, se sì clicca il seguente link per modificare la tua password http://localhost:8080/demo/recover/" + UUID;
                email.sendSimpleMessage(usernameDTO.getUsername(), "Password Recovery!", body);
            }
        }
    }

    /*GET /recover/{randomUUID} – Restituisce una pagina HTML contente una form per la
    sostituzione della password*/

    @GetMapping("/recover/{randomUUID}")
    public String register(@PathVariable("randomUUID") String randomUUID) { return "recover";}

        /***
         * funzione per generare un numero di conferma random
         * @return
         */
    public String generateUUID() {
        Random r=new Random();
        StringBuilder generatedString = new StringBuilder();
        for(int i=0; i<5; i++)
        generatedString.append(r.nextInt());

        return generatedString.toString();
    }

}
