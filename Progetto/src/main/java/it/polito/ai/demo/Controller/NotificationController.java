package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.DTO.NotificaTurnoDTO;
import it.polito.ai.demo.DTO.PrenotazioneDTONew;
import it.polito.ai.demo.DTO.TurnoDTO;
import it.polito.ai.demo.Entity.*;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Service.LineaService;
import it.polito.ai.demo.Service.TurnoService;
import it.polito.ai.demo.Service.UserService;
import it.polito.ai.demo.Service.UtenteRuoloService;
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

@Controller
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


    // Initialize Notifications
    private NotificaTurnoDTO notifications = NotificaTurnoDTO.builder().count(0).msg("").build();
    private Map<String, Integer> contatori = new HashMap<>();

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

            // Push notifications to front-end
            template.convertAndSendToUser(utente.getUserName(), "/queue/reply", notifications);
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
                        response.sendRedirect("/notifyC/" + user.getUserName() + "_" + iT.getData() + "_" + iT.getVerso());
                    return;
                }
                throw new NotFoundException("errore negli amministratori");
            }
            throw new NotFoundException("turno non trovato");
        }
        throw new NotFoundException("utente non trovato");
    }


    @GetMapping("/notifyC/{id}")
    public void sendConferma(@PathVariable("id") String id_turno) {

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

                // Push notifications to front-end
                template.convertAndSendToUser(utente.getUserName(), "/queue/reply", notifications);
            }
            throw new NotFoundException("turno non trovato no message!");
            // Increment Notification by one


            //System.out.println("Notifications successfully sent to Angular");

            //return "Notifications successfully sent to Angular !";
        }


    }
}
