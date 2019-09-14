package it.polito.ai.demo.Controller;

import it.polito.ai.demo.DTO.ChangePassDTO;
import it.polito.ai.demo.DTO.LoginDTO;
import it.polito.ai.demo.DTO.RegisterDTO;
import it.polito.ai.demo.DTO.UsernameDTO;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.UtenteRuolo;
import it.polito.ai.demo.Entity.idRuolo;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Exception.UnauthorizedException;
import it.polito.ai.demo.JWT.JwtTokenProvider;
import it.polito.ai.demo.Repository.UserRepository;
import it.polito.ai.demo.Service.EmailService;
import it.polito.ai.demo.Service.UserService;
import it.polito.ai.demo.Service.UtenteRuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;


@RestController
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
      Utente utente = userService.getUserById(username);
      if (utente != null) {


        List<UtenteRuolo> u = utenteRuoloService.getRuoli(username);
        List<String> ruoli = u.stream().map(x -> x.getId().getRuolo()).collect(toList());
        List<String> linee = u.stream().filter(x -> x.getUsername().equals(username)).map(UtenteRuolo::getNomeLinea).collect(toList());

        if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredcredential() == true) && (utente.getLocked() == true)) {

          token = jwtTokenProvider.createToken(username, ruoli, linee);

        } else {
          token = null;
        }

      } else {
        throw new UsernameNotFoundException("Username " + username + "not found");
      }




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

    if (userService.getUserById(registerDTO.getEmail()) != null) {
      throw new NotFoundException("utente già esistente");
    }
    if (registerDTO.getPassword().compareTo(registerDTO.getConfirmPassword()) != 0) {
      throw new NotFoundException("password diverse");
    }

    if (registerDTO.getPassword().length() < 6) {
      throw new NotFoundException("la password deve essere di almeno 6 caratteri");
    }

    if (registerDTO.getPassword().length() > 12) {
      throw new NotFoundException("la password non deve superare i 12 caratteri");
    }

    if (checkValidPass(registerDTO.getPassword(), registerDTO.getConfirmPassword()) == false) {
      throw new NotFoundException("password invalide");
    }

    String UUID = generateUUID();
    Utente u = Utente.builder()
      .UserName(registerDTO.getEmail())
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





  @PostMapping(path = "/recover")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void recoverPassword(@RequestBody UsernameDTO usernameDTO) {
    Utente utente = userService.getUserById(usernameDTO.getUsername());
    if (utente != null) {
      if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getLocked() == true)) {

        String UUID = generateUUID();
        utente.setToken(UUID);
        utente.setExpiredToken(new Date());
        userService.save(utente);
        String body = "Gentilissimo, confermi di aver richiesto il recupero della Password?, se sì inserisci il seguente codice per modificare la tua password: " + UUID;
        email.sendSimpleMessage(usernameDTO.getUsername(), "Password Recovery!", body);
        return;
      }
      throw new NotFoundException("non sei abilitato a procedere");
    }
    throw new BadCredentialsException("utente non valido");
  }


  @PostMapping("/recover/{UUID}")
  @ResponseStatus(HttpStatus.OK)
  public void checkRecoveryPass(@PathVariable("UUID") String UUID,
                                @RequestBody UsernameDTO usernameDTO) {
    Utente u = userService.getTokenForRecovery(UUID);
    if (u == null || u.getUserName().compareTo(usernameDTO.getUsername()) != 0)
      throw new BadRequestException("non sei abilitato a procedere");

    Date now = new Date();
    long diff = now.getTime() - u.getExpiredToken().getTime();
    if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione=1 h=3600000 ms
      throw new NotFoundException("token di registrazione scaduto o invalido");

    return;

  }


  @PostMapping("/recover/change")
  @ResponseStatus(HttpStatus.OK)
  public void checkRecoveryPass(@RequestBody RegisterDTO registerDTO) {

    Utente utente = userService.getUserById(registerDTO.getEmail());
    if (utente != null) {
      if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getLocked() == true)) {

        if (registerDTO.getPassword().compareTo(registerDTO.getConfirmPassword()) == 0) {
          utente.setExpiredcredential(true);
          utente.setExpiredToken(new Date(0));
          utente.setPassword(passwordEncoder.encode(registerDTO.getConfirmPassword()));
          userService.save(utente);
          String body = "Gentilissimo, le tue  credenziali d'accesso sono state modificate con successo!\nSaluti, la direzione.";
          email.sendSimpleMessage(utente.getUserName(), "Pedibus-Nuove Credenziali", body);
          return;
        }
        throw new BadRequestException("le due password non sono corrette");
      }
      throw new NotFoundException("non sei abilitato a procedere");
    }
    throw new BadCredentialsException("utente non valido");
  }


  @PostMapping(path = "/changepass")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void recoverPassword(HttpServletRequest req, @RequestBody ChangePassDTO changePassDTO) {
    String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
    Utente utente = userService.getUserById(username);
    if (utente != null) {
      if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredcredential() == true) && (utente.getLocked() == true)) {



        if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, changePassDTO.getPassword0())).isAuthenticated()) {

          if (changePassDTO.getPassword1().compareTo(changePassDTO.getPassword2()) == 0) {
            String UUID = generateUUID();
            utente.setExpiredcredential(true);
            utente.setToken(UUID);
            utente.setPassword(passwordEncoder.encode(changePassDTO.getPassword1()));
            utente.setExpiredToken(new Date());
            userService.save(utente);
            String body = "Gentilissimo, password cambiata come richiesto!";
            email.sendSimpleMessage(username, "Pedibus-Conferma modifica password!", body);
            return;
          }
          throw new BadCredentialsException("le password sono diverse");
        }
        throw new NotFoundException("la password vecchia non corretta");
      }
      throw new NotFoundException("token scaduto o invalido");
    }
    throw new BadCredentialsException("utente non valido");
  }


  @GetMapping(path = "/confirm/{randomUUID}")
  @ResponseStatus(HttpStatus.OK)
  public void confirmNuovoUser(@PathVariable("randomUUID") String randomUUID) {
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

  @GetMapping("/confirm/changepass/{randomUUID}")
  @ResponseStatus(HttpStatus.OK)
  public void checkChangePass(@PathVariable("randomUUID") String randomUUID) {

    Utente u = userService.getTokenForRecovery(randomUUID);
    Date now = new Date();
    long diff = now.getTime() - u.getExpiredToken().getTime();
    if (diff > 3600000) {
      // Tempo entro il quale poter confermare la registrazione=1 h=3600000 ms
      throw new NotFoundException("token di registrazione scaduto o invalido");
    }


    u.setExpiredcredential(true);
    u.setExpiredToken(new Date(0));
    userService.save(u);

    return;
  }


  @GetMapping(path = "/users")
  public List<UsernameDTO> altlistUsers(HttpServletRequest req) {

    String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
    List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));

    if (ru.contains("admin") || ru.contains("system-admin")) {
      List<Utente> users = userService.getAllUsers();

      List<UsernameDTO> usersList = new ArrayList<UsernameDTO>();

      for (Utente u : users)
        usersList.add(new UsernameDTO(u.getUserName()));

      return usersList;

    }
    throw new UnauthorizedException("errore");
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

  private boolean checkValidPass(String pass1, String pass2) {
    int l1 = pass1.length();
    int l2 = pass2.length();

    if (l1 > 7 && l1 < 20 && l2 > 7 && l2 < 20) {
      return true;
    }
    return false;
  }


  private String generatePassword() {
    Random r = new Random();
    StringBuilder generatedString = new StringBuilder();
    for (int i = 0; i < 5; i++)
      generatedString.append(r.nextInt());

    String p = generatedString.substring(0, 10);
    return p;
  }


}
