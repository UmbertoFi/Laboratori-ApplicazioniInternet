package it.polito.ai.demo.Controller;

import it.polito.ai.demo.DTO.LoginDTO;
import it.polito.ai.demo.DTO.RegisterDTO;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.UtenteRuolo;
import it.polito.ai.demo.Entity.idRuolo;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Exception.UnauthorizedException;
import it.polito.ai.demo.JWT.JwtTokenProvider;
import it.polito.ai.demo.Repository.UserRepository;
import it.polito.ai.demo.Service.EmailService;
import it.polito.ai.demo.Service.UserService;
import it.polito.ai.demo.Service.UtenteRuoloService;
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

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;


@RestController
//@RequestMapping(path = "demo")
public class UserController {


    @Autowired
    EmailService email;

    @Autowired
    UtenteRuoloService utenteRuoloService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;




    @PostMapping(path = "/login")
    public ResponseEntity signin(@RequestBody LoginDTO data) {
        String token;

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            Utente utente =userService.getUserById(username);
            if(utente!=null){

                //UtenteRuolo ruoli = utenteRuoloService.getUtenteRuolo(username, "*");

                List<UtenteRuolo> u=utenteRuoloService.getRuoli(username);
                List<String> ruoli=u.stream().map(x -> x.getId().getRuolo()).collect(toList());
                List<String>linee=u.stream().filter(x->x.getUsername().equals(username)).map(UtenteRuolo::getNomeLinea).collect(toList());

                if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredcredential() == true) && (utente.getLocked() == true)) {

                    token = jwtTokenProvider.createToken(username, ruoli, linee);

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
            throw new UnauthorizedException("");
        }
    }


    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void postNuovoUser(@RequestBody RegisterDTO registerDTO) {

        if (userService.getUserById(registerDTO.getEmail())!=null) {
            throw new NotFoundException("utente già esistente");
        }
        if (registerDTO.getPassword().compareTo(registerDTO.getConfirmPassword()) != 0) {
            throw new NotFoundException("password diverse");
        }

        if (registerDTO.getPassword().length() < 6){
          throw new NotFoundException("la password deve essere di almeno 6 caratteri");
        }

      if (registerDTO.getPassword().length() > 12){
        throw new NotFoundException("la password non deve superare i 12 caratteri");
      }

        if(checkValidPass(registerDTO.getPassword(), registerDTO.getConfirmPassword())==false){
            throw new NotFoundException("password invalide");
        }

        String UUID = generateUUID();
        Utente u = Utente.builder()
                .UserName(registerDTO.getEmail())
                //.Password(new BCryptPasswordEncoder(11).encode(registerDTO.getPassword()))
                .Password(passwordEncoder.encode(registerDTO.getPassword()))
                .token(UUID)
                .expiredToken(new Date())
                .enabled(false)
                .expiredAccount(true)//logica inversa
                .expiredcredential(true)//logica inversa
                .locked(true)//logica inversa
                .build();


        userService.save(u);


        String body = "Gentilissimo, confermi di esserti registrato al servizio?, se sì clicca il seguente link per confermare la registrazione http://localhost:8080/confirm/" + UUID;

        email.sendSimpleMessage(registerDTO.getEmail(), "Benvenuto!", body);

    }


    @GetMapping(path = "/confirm/{randomUUID}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void confirmNuovoUser(@PathVariable("randomUUID") String randomUUID) {
        Utente u = userService.getToken(randomUUID);
        if (u == null) {
            throw new NotFoundException("utente non valido");
        }
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione
            throw new NotFoundException("token scaduto");
        u.setEnabled(true);
        userService.save(u);

        UtenteRuolo ur;
        String ruolo;
        if (utenteRuoloService.getByRuoloSystemAdmin() == false) {
            ruolo = "system-admin";
        } else {
            ruolo = "user";
        }
        idRuolo id = idRuolo.builder()
                .utente(u)
                .ruolo(ruolo)
                .NomeLinea("*")
                .build();

        ur = UtenteRuolo.builder()
                .id(id)
                .build();

        utenteRuoloService.save(ur);
    }


    /* @PostMapping(path = "/recover")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void recoverPassword(@RequestBody UsernameDTO usernameDTO) {
        Utente utente = userService.getUserById(usernameDTO.getUsername());
        if (utente != null) {
            if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredcredential() == true) && (utente.getLocked() == true)) {

                String UUID = generateUUID();
                utente.setExpiredcredential(false);
                utente.setToken(UUID);
                utente.setExpiredToken(new Date());
                userService.save(utente);
                String body = "Gentilissimo, confermi di aver richiesto il recupero della Password?, se sì clicca il seguente link per modificare la tua password http://localhost:8080/recover/" + UUID;
                email.sendSimpleMessage(usernameDTO.getUsername(), "Password Recovery!", body);
                return;
            }
            throw new NotFoundException("token scaduto o invalido");
        }
    } */





    /* @GetMapping(path = "/users")
    public List<UsernameDTO> altlistUsers(HttpServletRequest req) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));

        if (ru.contains("admin") || ru.contains("system-admin") ) {
            List<Utente> users = userService.getAllUsers();

            List<UsernameDTO> usersList = new ArrayList<UsernameDTO>();

            for (Utente u : users)
                usersList.add(new UsernameDTO(u.getUserName()));

            return usersList;

        }
        throw new UnauthorizedException("errore");
    } */


    /* @PutMapping(path = "/users/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public void altmodifyRole(HttpServletRequest req, @PathVariable("userID") String userID, @RequestBody ModificaRuoloDTO modificaRuoloDTO) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));

        if(ru.contains("system-admin")) {
            if (modificaRuoloDTO.getAzione().compareTo("promuovi") == 0) {

                UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(userID, "*");

                if (utenteRuolo != null) {
                    idRuolo nuovoIdRuolo = idRuolo.builder()
                            .NomeLinea(modificaRuoloDTO.getLinea())
                            .username(userID)
                            .build();
                    UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                            .id(nuovoIdRuolo)
                            .ruolo("admin")
                            .build();
                    utenteRuoloService.save(nuovoRuolo);
                    return;
                }
                throw  new NotFoundException("errore");
            } else {
                UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(userID, modificaRuoloDTO.getLinea());
                if (utenteRuolo != null) {
                    utenteRuoloService.deleteOne(utenteRuolo);
                    return;
                }
                throw  new NotFoundException("errore");
            }
        }else if(ru.contains("admin") ){

            List<String> linee = jwtTokenProvider.getLinee(jwtTokenProvider.resolveToken(req));
            if(linee.contains(modificaRuoloDTO.getLinea())==true) {

                if (modificaRuoloDTO.getAzione().compareTo("promuovi") == 0) {

                    UtenteRuolo utenteRuolo2 = utenteRuoloService.getUtenteRuolo(userID, "*");

                    if (utenteRuolo2 != null) {
                        idRuolo nuovoIdRuolo = idRuolo.builder()
                                .NomeLinea(modificaRuoloDTO.getLinea())
                                .username(userID)
                                .build();
                        UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                                .id(nuovoIdRuolo)
                                .ruolo("admin")
                                .build();
                        utenteRuoloService.save(nuovoRuolo);
                        return;
                    }
                    throw  new NotFoundException("errore");
                } else {
                    UtenteRuolo utenteRuolo2 = utenteRuoloService.getUtenteRuolo(userID, modificaRuoloDTO.getLinea());
                    if (utenteRuolo2 != null) {
                        utenteRuoloService.deleteOne(utenteRuolo2);
                        return;
                    }
                    throw  new NotFoundException("errore");
                }

            }
        }
        throw  new UnauthorizedException("non autorizzato");

} */

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

    private boolean checkValidPass(String pass1, String pass2) {
        int l1=pass1.length();
        int l2=pass2.length();

        if(l1>7 && l1<20 && l2>7 && l2<20){
            return true;
        }
        return false;
    }



}
