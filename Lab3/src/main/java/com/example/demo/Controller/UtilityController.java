package com.example.demo.Controller;


import com.example.demo.DTO.*;
import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Linea;
import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.Utente;
import com.example.demo.Service.FermataService;
import com.example.demo.Service.LineaService;
import com.example.demo.Service.PrenotazioneService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UtilityController {

    @Autowired
    UserService userService;

    @Autowired
    LineaService lineaService;

    @Autowired
    FermataService fermataService;

    @Autowired
    PrenotazioneService prenotazioneService;

    @GetMapping(path = "/utility/checkUsername/{username}")
    public @ResponseBody
    ResponseEntity checkUsername(@PathVariable("username") String email) {

        Map<Object, Object> model = new HashMap<>();

        Utente utente = userService.getUserById(email);
        if (utente != null) {
            if ((utente.getEnabled() == true) && (utente.getExpiredAccount() == true) && (utente.getExpiredcredential() == true) && (utente.getLocked() == true)) {

                model.put("username", email);
                model.put("available", false);

                return ok(model);
            }
        }

        model.put("username", email);
        model.put("available", true);

        return ok(model);

    }

    @GetMapping(path = "/utility/linea/{nome_linea}")
    public @ResponseBody
    ResponseEntity convertNomeLinea(@PathVariable("nome_linea") String nomeLinea) {

        Linea l = lineaService.getLinea(nomeLinea);
        Map<Object, Object> model = new HashMap<>();

        if (l != null) {
            model.put("codice", l.getId());

            return ok(model);
        }
        model.put("codice", null);

        return ok(model);

    }

    @GetMapping(path = "/utility/fermata/{n_fermata}")
    public @ResponseBody
    ResponseEntity convertNumFermata(@PathVariable("n_fermata") String nfermata) {

        Optional<Fermata> f = fermataService.getFermata(Integer.parseInt(nfermata));

        Map<Object, Object> model = new HashMap<>();

        if (f.isPresent()== true) {
            model.put("nome_fermata", f.get().getNome());

            return ok(model);
        }
        model.put("nome_fermata", null);

        return ok(model);

    }



}
