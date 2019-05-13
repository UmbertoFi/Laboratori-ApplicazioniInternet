package com.example.demo.Controller;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.ModificaRuoloDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.DTO.UsernameDTO;
import com.example.demo.Entity.Utente;
import com.example.demo.Entity.UtenteRuolo;
import com.example.demo.Entity.idRuolo;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.Exception.UnauthorizedException;
import com.example.demo.JWT.JwtTokenProvider;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UtenteRuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    PasswordEncoder passwordEncoder;




    @PostMapping(path = "/login")
    public ResponseEntity signin(@RequestBody LoginDTO data) {
        String token;

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            Optional<Utente> u = users.findById(username);
            if (u.isPresent() == true) {
                Utente utente = u.get();

                UtenteRuolo ruoli = utenteRuoloService.getUtenteRuolo(username, "*");


                if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredCredential() == true) && (utente.getLocked() == true)) {

                    token = jwtTokenProvider.createToken(username, ruoli.getRuolo());

                } else {
                    token = null;
                }

            } else {
                throw new UsernameNotFoundException("Username " + username + "not found");
            }


            //String token = jwtTokenProvider.createToken(username, this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());


            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        }
    }





    @PostMapping(path = "/register")
    public @ResponseBody
    String postNuovoUser(@RequestBody RegisterDTO registerDTO) {

        if (userRepository.findById(registerDTO.getUsername()).isPresent()) {
            return "utente già esistente";
        }
        if (registerDTO.getPassword().compareTo(registerDTO.getPassword2()) != 0) {
            return "password diverse";

        }

        String UUID = generateUUID();
        Utente u = Utente.builder()
                .UserName(registerDTO.getUsername())
                //.Password(new BCryptPasswordEncoder(11).encode(registerDTO.getPassword()))
                .Password(passwordEncoder.encode(registerDTO.getPassword()))
                .token(UUID)
                .expiredToken(new Date())
                .enabled(false)
                .expiredAccount(true)//logica inversa
                .expiredCredential(true)//logica inversa
                .locked(true)//logica inversa
                .build();


        userService.save(u);


        String body = "Gentilissimo, confermi di esserti registrato al servizio?, se sì clicca il seguente link per confermare la registrazione http://localhost:8080/demo/confirm/" + UUID;
        email.sendSimpleMessage(registerDTO.getUsername(), "Benvenuto!", body);

        return "ok";
    }


    @GetMapping(path = "/confirm/{randomUUID}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void confirmNuovoUser(@PathVariable("randomUUID") String randomUUID) {
        Utente u = userService.getToken(randomUUID);
        if (u == null) {
            throw new NotFoundException();
        }
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione
            throw new NotFoundException();
        u.setEnabled(true);
        userService.save(u);

        idRuolo id = idRuolo.builder()
                .Username(u.getUserName())
                .NomeLinea("*")
                .build();

        UtenteRuolo ur;
        if (utenteRuoloService.getByRuoloSystemAdmin() == false) {
            ur = UtenteRuolo.builder()
                    .id(id)
                    .ruolo("system-admin")
                    .build();
        } else {
            ur = UtenteRuolo.builder()
                    .id(id)
                    .ruolo("user")
                    .build();
        }
        utenteRuoloService.save(ur);
    }


    @PostMapping(path = "/recover")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void recoverPassword(@RequestBody UsernameDTO usernameDTO) {
        Utente utente = userService.getUserById(usernameDTO.getUsername());
        if (utente != null) {
            if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredCredential() == true) && (utente.getLocked() == true)) {

                String UUID = generateUUID();
                utente.setExpiredCredential(false);
                utente.setToken(UUID);
                utente.setExpiredToken(new Date());
                userService.save(utente);
                String body = "Gentilissimo, confermi di aver richiesto il recupero della Password?, se sì clicca il seguente link per modificare la tua password http://localhost:8080/demo/recover/" + UUID;
                email.sendSimpleMessage(usernameDTO.getUsername(), "Password Recovery!", body);
            }
        }
    }


    /***
     * funzione per generare un numero di conferma random
     * @return
     */
    public String generateUUID() {
        Random r = new Random();
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < 5; i++)
            generatedString.append(r.nextInt());

        return generatedString.toString();
    }


    @GetMapping(path = "/users")
    public List<UsernameDTO> listUsers(HttpServletRequest req) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));

        List<UtenteRuolo> ruoli = utenteRuoloService.getAll();
        for (UtenteRuolo ur : ruoli) {
            if (ur.getId().getUsername().compareTo(username) == 0) {
                if (ur.getRuolo().compareTo("admin") == 0 || ur.getRuolo().compareTo("system-admin") == 0) {
                    List<Utente> users = userService.getAllUsers();

                    List<UsernameDTO> usersList = new ArrayList<UsernameDTO>();

                    for (Utente u : users)
                        usersList.add(new UsernameDTO(u.getUserName()));

                    return usersList;
                }
            }
        }
        throw new UnauthorizedException();
    }


    @PutMapping(path = "/users/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyRole(HttpServletRequest req, @PathVariable("userID") String userID, @RequestBody ModificaRuoloDTO modificaRuoloDTO) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));

        List<UtenteRuolo> ruoli = utenteRuoloService.getAll();
        for (UtenteRuolo ur : ruoli) {
            if (ur.getId().getUsername().compareTo(username) == 0) {
                if (ur.getRuolo().compareTo("system-admin") == 0) {
                    if (modificaRuoloDTO.getAzione().compareTo("promuovi") == 0) {
                        for (UtenteRuolo ur2 : ruoli) {
                            if (ur2.getId().getUsername().compareTo(userID) == 0) {
                                idRuolo nuovoIdRuolo = idRuolo.builder()
                                        .NomeLinea(modificaRuoloDTO.getLinea())
                                        .Username(userID)
                                        .build();
                                UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                                        .id(nuovoIdRuolo)
                                        .ruolo("admin")
                                        .build();
                                utenteRuoloService.save(nuovoRuolo);
                                return;

                            }
                        }
                    } else {
                        for (UtenteRuolo ur2 : ruoli) {
                            if (ur2.getId().getUsername().compareTo(userID) == 0 && ur2.getId().getNomeLinea().compareTo(modificaRuoloDTO.getLinea()) == 0 && ur2.getRuolo().compareTo("admin") == 0) {
                                utenteRuoloService.deleteOne(ur2);
                                return;
                            }
                        }
                    }

                } else if (ur.getRuolo().compareTo("admin") == 0 || ur.getId().getNomeLinea().compareTo(modificaRuoloDTO.getLinea()) == 0) {
                    if (modificaRuoloDTO.getAzione().compareTo("promuovi") == 0) {
                        for (UtenteRuolo ur2 : ruoli) {
                            if (ur2.getId().getUsername().compareTo(userID) == 0) {
                                idRuolo nuovoIdRuolo = idRuolo.builder()
                                        .NomeLinea(modificaRuoloDTO.getLinea())
                                        .Username(userID)
                                        .build();
                                UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                                        .id(nuovoIdRuolo)
                                        .ruolo("admin")
                                        .build();
                                utenteRuoloService.save(nuovoRuolo);
                                return;
                            }
                        }
                    } else {
                        for (UtenteRuolo ur2 : ruoli) {
                            if (ur2.getId().getUsername().compareTo(userID) == 0 && ur2.getId().getNomeLinea().compareTo(modificaRuoloDTO.getLinea()) == 0 && ur2.getRuolo().compareTo("admin") == 0) {
                                utenteRuoloService.deleteOne(ur2);
                                return;
                            }
                        }
                    }
                }
            }
        }
        throw new UnauthorizedException();
    }

}
