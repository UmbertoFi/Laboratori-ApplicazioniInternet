package com.example.demo.Controller;


import com.example.demo.DTO.*;
import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    BambinoService bambinoService;

    @Autowired
    CorsaService corsaService;

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

    @GetMapping(path = "/utility/children")
    public @ResponseBody
    List<BambinoDTO> getAllBambini() throws Exception {
        Iterable<Bambino> children = bambinoService.getBambini();
        List<BambinoDTO> nomiBambini = new ArrayList<>();
        for (Bambino bambino : children)
            nomiBambini.add(bambino.convertToBambinoDTO());
        return nomiBambini;
    }

    @GetMapping(path = "/utility/corse")
    public @ResponseBody
    List<CorsaSDTO> getAllCorse() throws Exception {

        Iterable<Corsa> cors = corsaService.getCorse();
        List<CorsaSDTO> corse = new ArrayList<>();
        for (Corsa c : cors)
            corse.add(c.convertToDTO());
        return corse;
    }

    @GetMapping(path = "/utility/corse/{nome_linea}")
    public @ResponseBody
    List<CorsaSDTO> getAllCorse(@PathVariable("nome_linea") String nomeLinea) throws Exception {

        Integer n_linea=lineaService.getLinea(nomeLinea).getId();

        Iterable<Corsa> cors = corsaService.getCorseByIdLinea(n_linea);
        List<CorsaSDTO> corse = new ArrayList<>();
        for (Corsa c : cors)
            if (c.getId().getVerso().compareTo("A")==0)
                corse.add(c.convertToDTO());
        return corse;
    }

    @GetMapping(path = "/utility/reservations/{nome_linea}/{date}")
    public @ResponseBody
    PasseggeriDTONew getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                               @PathVariable("date") String date) {


        String[] pieces = date.split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

        List<Fermata> fermate = fermataService.getFermateList(nomeLinea);

        List<Prenotazione> prenotazioni_A = new ArrayList<Prenotazione>();
        List<Prenotazione> prenotazioni_R = new ArrayList<Prenotazione>();

        List<DettagliLineaPersoneDTONew> fermateAndata = new ArrayList<>();
        List<DettagliLineaPersoneDTONew> fermateRitorno = new ArrayList<>();


        Iterable<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioni();
        for (Prenotazione p : prenotazioni) {
            if (p.getId().getData().equals(data)) {
                if (p.getId().getVerso().equals("A"))
                    prenotazioni_A.add(p);
                else
                    prenotazioni_R.add(p);
            }
        }

        for (Fermata f2 : fermate) {
            List<PersonaDTONew> persone = new ArrayList<>();
            for (Prenotazione p1 : prenotazioni_A) {
                if (p1.getFermata().getId() == f2.getId()) {
                    persone.add(new PersonaDTONew(bambinoService.getNome(p1.getId().getId_bambino()),p1.isPresente()));
                }
            }
            DettagliLineaPersoneDTONew dlp = f2.convertToDettagliLineaPersoneDTONew(persone,0);
            fermateAndata.add(dlp);
        }

        Collections.reverse(fermate);
        for (Fermata f2 : fermate) {
            List<PersonaDTONew> persone = new ArrayList<>();
            for (Prenotazione p1 : prenotazioni_R) {
                if (p1.getFermata().getId() == f2.getId()) {
                    persone.add(new PersonaDTONew(bambinoService.getNome(p1.getId().getId_bambino()),p1.isPresente()));
                }
            }
            DettagliLineaPersoneDTONew dlp2 = f2.convertToDettagliLineaPersoneDTONew(persone,1);
            fermateRitorno.add(dlp2);
        }


        PasseggeriDTONew dettagliLineaPersone = new PasseggeriDTONew();
        dettagliLineaPersone.setFermateA(fermateAndata);
        dettagliLineaPersone.setFermateR(fermateRitorno);

        return dettagliLineaPersone;

    }

}
