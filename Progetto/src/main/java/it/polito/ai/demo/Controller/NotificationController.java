package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.*;
import it.polito.ai.demo.Entity.*;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    TurnoService turnoService;
    @Autowired
    UserService userService;
    @Autowired
    LineaService lineaService;
    @Autowired
    UtenteRuoloService utenteRuoloService;
    @Autowired
    FermataService fermataService;
    @Autowired
    PrenotazioneService prenotazioneService;
    @Autowired
    BambinoService bambinoService;
    @Autowired
    CorsaService corsaService;


    // Initialize Notifications
    private NotificaTurnoDTO notifications = NotificaTurnoDTO.builder().count(0).msg("").build();
    private Map<String, Integer> contatori = new HashMap<>();



    @PostMapping("/utility/zero/{username}")
    public void azzeraCounter(@PathVariable("username") String username) {
      if (contatori.containsKey(username)) {
            contatori.put(username, 0);
        }
    }

    @GetMapping("utility/primovalorenotifiche/{username}")
    public @ResponseBody int initializeCounter(@PathVariable("username") String username){
      int x;
      if (contatori.containsKey(username)) {
        x = contatori.get(username);
      }
      else x = 0;

      return x;
    }


    @GetMapping("/notifyT/{id}")
    public void sendTurno(@PathVariable("id") String id_turno) {

        //System.out.println("notify ci sono");
        String[] pieces = id_turno.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        Utente utente = userService.getUserById(pieces[0]);
        idTurno iT = idTurno.builder()
                .data(data)
                .utente(utente)
                .verso(pieces[2])
                .build();
        if (turnoService.getTurnoById(iT).isPresent()) {
            if (!contatori.containsKey(utente.getUserName())) {
                contatori.put(utente.getUserName(), 0);
            }

            Integer x = contatori.get(utente.getUserName());
            contatori.put(utente.getUserName(), x + 1);

            notifications.setCount(contatori.get(utente.getUserName()));
            notifications.setMsg("Nuovo turno!");
            notifications.setData(data.toString());
            notifications.setVerso(pieces[2]);
            notifications.setUtente(utente.getUserName());
            notifications.setLinea(turnoService.getTurnoById(iT).get().getLinea().getNome());
            notifications.setTipo(1);

            // Push notifications to front-end
            template.convertAndSendToUser(utente.getUserName(), "/queue/reply", notifications);

            return;
        }
        throw new NotFoundException("turno non trovato no message!");
        // Increment Notification by one


        //System.out.println("Notifications successfully sent to Angular");

        //return "Notifications successfully sent to Angular !";
    }


    @PostMapping(path = "/utility/turno")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void postConsolidaTurno(@RequestBody DisponibilitaGetDTO disponibilitaGetDTO, HttpServletResponse response) throws IOException {


        // Controllare se esiste già un turno di quella persona in quel verso in quella data
        // se è già presente lo blocca, altrimenti lo inserisce

        String[] pieces = disponibilitaGetDTO.getData().split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

        String verso;
        if (disponibilitaGetDTO.getVerso().compareTo("Andata") == 0)
            verso = "A";
        else
            verso = "R";
        idTurno id = idTurno.builder()
                .utente(userService.getUserById(disponibilitaGetDTO.getUsername()))
                .data(date)
                .verso(verso)
                .build();

        Optional<Turno> t = turnoService.getTurnoById(id);
        if (!t.isPresent()) {
            Turno t2 = Turno.builder()
                    .id(id)
                    .linea(lineaService.getLinea(disponibilitaGetDTO.getLinea()))
                    .consolidato(false)
                    .build();
            turnoService.save(t2);
            //notificationController.getNotification();
            //System.out.println(id.getUtente().getUserName()+"_"+id.getData()+"_"+id.getVerso());
            response.sendRedirect("/notifyT/" + id.getUtente().getUserName() + "_" + id.getData() + "_" + id.getVerso());

            return;
        }
        throw new NotFoundException("Turno già presente!");

    }

    @PutMapping(path = "/utility/confirm/turno")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void confermaTurno(@RequestBody TurnoDTO turnoDTO, HttpServletResponse response) throws IOException {


        String[] dataPieces = turnoDTO.getData().split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        Utente u = userService.getUserById(turnoDTO.getUtente());

        if (u != null) {
            idTurno iT = idTurno.builder()
                    .data(data)
                    .utente(u)
                    .verso(turnoDTO.getVerso())
                    .build();

            Optional<Turno> t = turnoService.getTurnoById(iT);
            if (t.isPresent()) {
                if (t.get().getConsolidato() == true)
                    t.get().setConsolidato(false);
                else
                    t.get().setConsolidato(true);
                turnoService.save(t.get());

                List<Utente> admin = utenteRuoloService.getAdminByLinea(t.get().getLinea().getNome());
                if (admin != null) {
                    for (Utente user : admin)
                        response.sendRedirect("/notifyC/" + iT.getUtente().getUserName() + "_" + iT.getData() + "_" + iT.getVerso()+"/"+user.getUserName());
                    return;
                }
                throw new NotFoundException("errore negli amministratori");
            }
            throw new NotFoundException("turno non trovato");
        }
        throw new NotFoundException("utente non trovato");
    }


    @GetMapping("/notifyC/{id}/{amministratore}")
    public void sendConferma(@PathVariable("id") String id_turno,
                             @PathVariable("amministratore") String admin) {

        //System.out.println("notify ci sono");
        String[] pieces = id_turno.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        Utente utente = userService.getUserById(pieces[0]);
        idTurno iT = idTurno.builder()
                .data(data)
                .utente(utente)
                .verso(pieces[2])
                .build();
        if (turnoService.getTurnoById(iT).isPresent()) {
            if (turnoService.getTurnoById(iT).isPresent()) {
                if (!contatori.containsKey(utente.getUserName())) {
                    contatori.put(utente.getUserName(), 0);
                }

                Integer x = contatori.get(utente.getUserName());
                contatori.put(utente.getUserName(), x + 1);

                notifications.setCount(contatori.get(utente.getUserName()));
                notifications.setMsg("Nuova Conferma Turno!");
                notifications.setData(data.toString());
                notifications.setVerso(pieces[2]);
                notifications.setUtente(utente.getUserName());
                notifications.setLinea(turnoService.getTurnoById(iT).get().getLinea().getNome());
                notifications.setTipo(2);
                // Push notifications to front-end
                template.convertAndSendToUser(admin, "/queue/reply", notifications);

                return;
            }
            throw new NotFoundException("turno non trovato no message!");
            // Increment Notification by one


            //System.out.println("Notifications successfully sent to Angular");

            //return "Notifications successfully sent to Angular !";
        }


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



    @GetMapping("/notifyP/{id}/{action}/{username}/{accompagnatore}")
    public void sendPrenotazione(@PathVariable("id") String id_prenotazione,
                                 @PathVariable("action") String azione,
                                 @PathVariable("username") String username,
                                 @PathVariable("accompagnatore") String accompagnatore) {

        //System.out.println("notify ci sono");
        String[] pieces = id_prenotazione.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        Utente utente = userService.getUserById(username);

        if(utente!=null) {
            idPrenotazione iP = idPrenotazione.builder()
                    .data(data)
                    .id_bambino(Integer.parseInt(pieces[0]))
                    .verso(pieces[2])
                    .build();

            if (prenotazioneService.getPrenotazione(iP).isPresent()) {
                Prenotazione p=prenotazioneService.getPrenotazione(iP).get();
                    if (!contatori.containsKey(utente.getUserName())) {
                        contatori.put(utente.getUserName(), 0);
                    }

                    Integer x = contatori.get(utente.getUserName());
                    contatori.put(utente.getUserName(), x + 1);

                    Bambino b=bambinoService.getBambinoById(iP.getId_bambino());
                    if(b!=null) {
                        notifications.setCount(contatori.get(utente.getUserName()));
                        notifications.setMsg("Il genitore " + utente.getUserName() + " ha " + azione + "la prenotazione");
                        notifications.setData(data.toString());
                        notifications.setVerso(pieces[2]);
                        notifications.setUtente(b.getNome()+" "+b.getCognome());
                        notifications.setLinea(p.getCorsa().getLinea().getNome());
                        notifications.setTipo(3);
                        // Push notifications to front-end
                        template.convertAndSendToUser(accompagnatore, "/queue/reply", notifications);


                        return;
                    }
                throw new NotFoundException("bambino non trovato!");
                }
                throw new NotFoundException("turno non trovato no message!");
                // Increment Notification by one


                //System.out.println("Notifications successfully sent to Angular");

                //return "Notifications successfully sent to Angular !"
        }
        throw new NotFoundException("utente non trovato");

    }

    @DeleteMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void deletePrenotazione(@PathVariable("nome_linea") String nomeLinea,
                            @PathVariable("date") String date,
                            @PathVariable("reservation_id") String res_id) {


        String[] pieces = res_id.split("_");
        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        idPrenotazione iP = idPrenotazione.builder()
                .data(data)
                .id_bambino(Integer.parseInt(pieces[0]))
                .verso(pieces[2])
                .build();

        Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
        if (p.isPresent()) {
            prenotazioneService.deleteOne(p.get());
            return ;
        }
        throw new BadRequestException("errore nella cancellazione");
    }


}
