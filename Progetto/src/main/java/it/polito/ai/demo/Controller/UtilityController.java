package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.*;
import it.polito.ai.demo.Entity.*;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Exception.UnauthorizedException;
import it.polito.ai.demo.JWT.JwtTokenProvider;
import it.polito.ai.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UtenteRuoloService utenteRuoloService;

    @Autowired
    DisponibilitaService disponibilitaService;

    @Autowired
    TurnoService turnoService;

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

    /* @GetMapping(path = "/utility/linea/{nome_linea}")
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

    } */

    /* @GetMapping(path = "/utility/fermata/{n_fermata}")
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
    } */

    @GetMapping(path = "/utility/children")
    public @ResponseBody
    List<BambinoDTO> getAllBambini() throws Exception {
        Iterable<Bambino> children = bambinoService.getBambini();
        List<BambinoDTO> nomiBambini = new ArrayList<>();
        for (Bambino bambino : children)
            nomiBambini.add(bambino.convertToBambinoDTO());
        return nomiBambini;
    }

    @PostMapping(path = "/utility/children/{username}")
    public @ResponseBody
    void postNuovoBambino(@PathVariable("username") String username,
                          @RequestBody BambinoNewDTO bambinoNewDTO) {
        Utente u = userService.getUserById(username);
        if (u != null) {
            if (!bambinoService.getBambinoByAll(bambinoNewDTO.getNome(), bambinoNewDTO.getCognome(), u).isPresent()) {
                Bambino b = bambinoNewDTO.convertToEntity(u);
                bambinoService.save(b);
                return;
            }
            throw new BadRequestException("bambino già inserito");
        }
        throw new BadRequestException("username non trovato");
    }

    @GetMapping(path = "/utility/children/{username}")
    public @ResponseBody
    List<BambinoDTO> getAllBambini(@PathVariable("username") String username) {

        Utente u = userService.getUserById(username);
        if (u != null) {
            List<BambinoDTO> children = bambinoService.getFigli(u);

            if (children == null) {
                children=new ArrayList<>();
                // throw new BadRequestException("bambini non trovati!");
            }

            return children;
        }
        throw new BadRequestException("username non valido!");

    }

    /* @GetMapping(path = "/utility/corse")
    public @ResponseBody
    List<CorsaSDTO> getAllCorse() throws Exception {

        Iterable<Corsa> cors = corsaService.getCorse();
        List<CorsaSDTO> corse = new ArrayList<>();
        for (Corsa c : cors)
            corse.add(c.convertToDTO());
        return corse;
    } */

    @GetMapping(path = "/utility/corse/{nome_linea}")
    public @ResponseBody
    List<CorsaSDTO> getAllCorse(@PathVariable("nome_linea") String nomeLinea) throws Exception {

        Integer n_linea = lineaService.getLinea(nomeLinea).getId();

        Iterable<Corsa> cors = corsaService.getCorseByIdLinea(n_linea);
        List<CorsaSDTO> corse = new ArrayList<>();
        for (Corsa c : cors)
            if (c.getVerso().compareTo("A") == 0)
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
                    persone.add(new PersonaDTONew(p1.getId().getId_bambino(), bambinoService.getNome(p1.getId().getId_bambino()), bambinoService.getCognome(p1.getId().getId_bambino()), p1.isPresente()));
                }
            }
            DettagliLineaPersoneDTONew dlp = f2.convertToDettagliLineaPersoneDTONew(persone, 0);
            fermateAndata.add(dlp);
        }

        Collections.reverse(fermate);
        for (Fermata f2 : fermate) {
            List<PersonaDTONew> persone = new ArrayList<>();
            for (Prenotazione p1 : prenotazioni_R) {
                if (p1.getFermata().getId() == f2.getId()) {
                    persone.add(new PersonaDTONew(p1.getId().getId_bambino(), bambinoService.getNome(p1.getId().getId_bambino()), bambinoService.getCognome(p1.getId().getId_bambino()), p1.isPresente()));
                }
            }
            DettagliLineaPersoneDTONew dlp2 = f2.convertToDettagliLineaPersoneDTONew(persone, 1);
            fermateRitorno.add(dlp2);
        }


        PasseggeriDTONew dettagliLineaPersone = new PasseggeriDTONew();
        dettagliLineaPersone.setFermateA(fermateAndata);
        dettagliLineaPersone.setFermateR(fermateRitorno);

        return dettagliLineaPersone;

    }

    @PostMapping(path = "/utility/reservations/{nome_linea}/{date}")
    public @ResponseBody
    IdPrenotazioneDTO postNuovaPrenotazione(@PathVariable("nome_linea") String nomeLinea,
                                            @PathVariable("date") String date,
                                            @RequestBody PrenotazioneDTONew prenotazioneDTO) {


        Optional<Fermata> f = fermataService.getFermata(prenotazioneDTO.getId_fermata());
        if (f.isPresent()) {

            String[] pieces = date.split("-");
            LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
            LocalDate curr = LocalDate.now();

            idPrenotazione iP = idPrenotazione.builder()
                    .data(data)
                    .id_bambino(prenotazioneDTO.getId_bambino())
                    .verso(prenotazioneDTO.getVerso())
                    .build();

            if (prenotazioneService.getPrenotazione(iP).isPresent()) {
                // UPDATE
                Prenotazione p = prenotazioneService.getPrenotazione(iP).get();
                // if(p.getFermata().getLinea()!=fermataService.getFermata(prenotazioneDTO.getId_fermata()).get().getLinea()) {
                f.get().getPrenotazioni().remove(p);
                p.setFermata(f.get());
                prenotazioneService.save(p);
                f.get().getPrenotazioni().add(p);
                return IdPrenotazioneDTO.builder().id(iP.toString()).build();
                //}
            } else {
                // INSERT
                boolean presente;
                if (data.compareTo(curr) == 0) {
                    presente = true;
                } else {
                    presente = false;
                }
                Prenotazione p = Prenotazione.builder()
                        .fermata(f.get())
                        .presente(presente)
                        .corsa(corsaService.getCorsa(f.get().getLinea().getId(), data, prenotazioneDTO.getVerso()))
                        .id(iP)
                        .build();

                prenotazioneService.save(p);
                f.get().getPrenotazioni().add(p);
                return IdPrenotazioneDTO.builder().id(iP.toString()).build();
            }
        }
        throw new NotFoundException("errore nella prenotazione");
    }

    @PutMapping(path = "/utility/reservations/{date}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void updatePrenotazione(@PathVariable("date") String date,
                            @RequestBody PrenotazioneDTONew prenotazioneDTO) {


        String[] dataPieces = date.split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        idPrenotazione iP = idPrenotazione.builder()
                .data(data)
                .id_bambino(prenotazioneDTO.getId_bambino())
                .verso(prenotazioneDTO.getVerso())
                .build();

        Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
        if (p.isPresent()) {
            if (p.get().isPresente() == true)
                p.get().setPresente(false);
            else
                p.get().setPresente(true);
            prenotazioneService.save(p.get());
            return;
        }

        throw new BadRequestException("errore nella modifica ");
    }

    @PostMapping(path = "/utility/available/{username}")
    public @ResponseBody
    void postNuovaPrenotazione(@PathVariable("username") String username,
                                @RequestBody DisponibilitaDTO disponibilitaDTO) {

        Utente u=userService.getUserById(username);
        if(u!=null){
            String[] pieces = disponibilitaDTO.getData().split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

            Linea l =lineaService.getLinea(disponibilitaDTO.getLinea());
           String verso;
            if(l!=null){
                if(disponibilitaDTO.getVerso().compareTo("Andata")==0){
                    verso="A";
                }
                else{
                    verso="R";
                }

                Corsa c=corsaService.getCorsa(l.getId(),date, verso);
                if(c!=null){

                    Disponibilita d=Disponibilita.builder()
                                        .id(idDisponibilita.builder().corsa(c).utente(u).build())
                           /* .fermataA()
                            .fermataR()*/
                            .build();

                            disponibilitaService.save(d);

                            return;
                }
                throw  new BadRequestException("corsa non esistente");
            }
            throw  new BadRequestException("linea non esistente");
        }
        throw  new BadRequestException("utente non esistente");

    }

    @PutMapping(path = "/utility/modificaRuolo")
    @ResponseStatus(HttpStatus.OK)
    public void modifyRole(HttpServletRequest req, @RequestBody ModificaRuoloDTO modificaRuoloDTO) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));


        if(ru.contains("system-admin")) {
            if (modificaRuoloDTO.getAzione().compareTo("Promuovi") == 0) {


                Utente u=userService.getUserById(modificaRuoloDTO.getUsername());
                if(u!=null) {
                    UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(),modificaRuoloDTO.getLinea(), "admin");



                    if (utenteRuolo == null) {
                        idRuolo nuovoIdRuolo = idRuolo.builder()
                                .NomeLinea(modificaRuoloDTO.getLinea())
                                .utente(u)
                                .ruolo("admin")
                                .build();
                        UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                                .id(nuovoIdRuolo)
                                .build();
                        utenteRuoloService.save(nuovoRuolo);
                        return;
                    }
                    throw new NotFoundException(modificaRuoloDTO.getUsername()+" già admin per la linea "+modificaRuoloDTO.getLinea());
                }
                throw new NotFoundException("errore nell' utente(system-admin)");
            } else {
                UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");
                if (utenteRuolo != null) {
                    utenteRuoloService.deleteOne(utenteRuolo);
                    return;
                }
                throw  new NotFoundException(modificaRuoloDTO.getUsername()+" non è ancora admin per la linea "+modificaRuoloDTO.getLinea());
            }
        }else if(ru.contains("admin") ){

            List<String> linee = jwtTokenProvider.getLinee(jwtTokenProvider.resolveToken(req));
            if(linee.contains(modificaRuoloDTO.getLinea())==true) {

                if (modificaRuoloDTO.getAzione().compareTo("Promuovi") == 0) {

                    Utente u2=userService.getUserById(modificaRuoloDTO.getUsername());

                    if(u2!=null) {
                        UtenteRuolo utenteRuolo2 = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");

                        if (utenteRuolo2 == null) {
                            idRuolo nuovoIdRuolo = idRuolo.builder()
                                    .NomeLinea(modificaRuoloDTO.getLinea())
                                    .utente(u2)
                                    .ruolo("admin")
                                    .build();
                            UtenteRuolo nuovoRuolo = UtenteRuolo.builder()
                                    .id(nuovoIdRuolo)

                                    .build();
                            utenteRuoloService.save(nuovoRuolo);
                            return;
                        }
                        throw new NotFoundException(modificaRuoloDTO.getUsername()+" già admin per la linea "+modificaRuoloDTO.getLinea());
                    }
                    throw new NotFoundException("errore nell' utente(admin)");
                } else {
                    UtenteRuolo utenteRuolo2 = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");
                    if (utenteRuolo2 != null) {
                        utenteRuoloService.deleteOne(utenteRuolo2);
                        return;
                    }
                    throw  new NotFoundException(modificaRuoloDTO.getUsername()+" non ancora admin per la linea "+modificaRuoloDTO.getLinea());
                }

            }
        }
        throw  new UnauthorizedException("non autorizzato");

    }


    @GetMapping(path = "/utility/disponibilita/{linea}/{data}/{verso}")
    public @ResponseBody
    List<DisponibilitaGetDTO> postDisponibilita(@PathVariable("linea") String nomeLinea,
                                            @PathVariable("data") String date,
                                            @PathVariable("verso") String verso) {



        String[] pieces = date.split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
        String v;
        if(verso.compareTo("Andata")==0)
            v="A";
        else
            v="R";

        Corsa c = corsaService.getCorsa(lineaService.getLinea(nomeLinea).getId(),data,v);
        if(c!=null){
            List<DisponibilitaGetDTO> disponibilita = disponibilitaService.getDisponibilitaByCorsa(c.getId());
            if(!disponibilita.isEmpty()){
                return disponibilita;
            }
            throw new NotFoundException ("Nessuna disponibilità per la corsa scelta!");
        }
        throw new NotFoundException ("Corsa non trovata!");
    }


    @PostMapping(path = "/utility/turno")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void postConsolidaTurno(@RequestBody DisponibilitaGetDTO disponibilitaGetDTO) {


        // Controllare se esiste già un turno di quella persona in quel verso in quella data
        // se è già presente lo blocca, altrimenti lo inserisce

        String[] pieces = disponibilitaGetDTO.getData().split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

        String verso;
        if(disponibilitaGetDTO.getVerso().compareTo("Andata")==0)
            verso="A";
        else
            verso="R";
        idTurno id = idTurno.builder()
                .utente(userService.getUserById(disponibilitaGetDTO.getUsername()))
                .data(date)
                .verso(verso)
                .build();

        Optional<Turno> t = turnoService.getTurnoById(id);
        if(!t.isPresent()){
            Turno t2 = Turno.builder()
                    .id(id)
                    .linea(lineaService.getLinea(disponibilitaGetDTO.getLinea()))
                    .build();
            turnoService.save(t2);
            return;
        }
        throw new NotFoundException ("Turno già presente!");

    }


    @GetMapping(path = "/utility/ruoli")
    public @ResponseBody
    List<RuoloDTO> getAllCorse(HttpServletRequest req){

        List<RuoloDTO> ruoli=new ArrayList<>();

        List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));

        for(String s:ru) {
            ruoli.add(RuoloDTO.builder().ruolo(s).build());
        }
        return ruoli;
    }
}
