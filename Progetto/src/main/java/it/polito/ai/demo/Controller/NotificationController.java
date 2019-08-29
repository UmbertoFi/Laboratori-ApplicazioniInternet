package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.*;
import it.polito.ai.demo.Entity.*;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.JWT.JwtTokenProvider;
import it.polito.ai.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
public class NotificationController { //ciao antonino
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

    @Autowired
    JwtTokenProvider jwtTokenProvider;


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

        System.out.println("NOTIFICA CONSOLIDA TURNO");
        String[] pieces = id_turno.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]),
          Integer.parseInt(dataPieces[2]));
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

                response.sendRedirect("/notifyC/" + iT.getUtente().getUserName() + "_" + iT.getData() + "_" + iT.getVerso()+"/"+t.get().getLinea().getNome());
                return;

            }
            throw new NotFoundException("turno non trovato");
        }
        throw new NotFoundException("utente non trovato");
    }


    @PutMapping("/notifyC/{id}/{linea}")
    public void sendConferma(@PathVariable("id") String id_turno,
                             @PathVariable("linea") String linea) {

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
                notifications.setMsg("NUOVA Presa Visione!");
                notifications.setData(data.toString());
                notifications.setVerso(pieces[2]);
                notifications.setUtente(utente.getUserName());
                notifications.setLinea(linea);
                notifications.setTipo(2);
                // Push notifications to front-end

                List<Utente> admin = utenteRuoloService.getAdminByLinea(linea);
                if (admin != null) {
                    for (Utente user : admin) {
                      template.convertAndSendToUser(user.getUserName(), "/queue/reply", notifications);
                    }
                    return;
                }
                throw new NotFoundException("errore negli amministratori");
            }
            throw new NotFoundException("turno non trovato no message!");
            // Increment Notification by one


            //System.out.println("Notifications successfully sent to Angular");

            //return "Notifications successfully sent to Angular !";
        }


    }

    // LATO ACCOMPAGNATORE possibile solo se accompagnatore di quella linea
  @PostMapping(path = "/utility/accompagnatore/reservations/{nome_linea}/{date}")
  public @ResponseBody
  IdPrenotazioneDTO postNuovaPrenotazioneAccompagnatore(@PathVariable("nome_linea") String nomeLinea,
                                          @PathVariable("date") String date,
                                          @RequestBody PrenotazioneDTONew prenotazioneDTO,
                                          HttpServletResponse response, HttpServletRequest req) throws IOException {


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
        List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
        if (accompagnatori != null) {
          for (Utente u : accompagnatori) {
            if (u.getUserName().compareTo(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))) == 0) {
              f.get().getPrenotazioni().remove(p);
              p.setFermata(f.get());
              prenotazioneService.save(p);
              f.get().getPrenotazioni().add(p);

              response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/modificato/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));

              return IdPrenotazioneDTO.builder().id(iP.toString()).build();
            }
          }
        }
        throw new BadRequestException("Errore: accompagnatore non autorizzato poichè non è accompagnatore della linea");
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
        List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
        if (accompagnatori != null) {
          for (Utente u : accompagnatori) {
            if (u.getUserName().compareTo(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))) == 0) {
              prenotazioneService.save(p);
              f.get().getPrenotazioni().add(p);

              response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/effettuato/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));

              return IdPrenotazioneDTO.builder().id(iP.toString()).build();

            }
          }
        }
        throw new BadRequestException("Errore: accompagnatore non autorizzato poichè non è accompagnatore della linea");
      }
    }
    throw new NotFoundException("errore nella prenotazione");
  }

  // LATO ACCOMPAGNATORE possibile solo se accompagnatore di quella linea
  @PutMapping(path = "/utility/accompagnatore/reservations/{date}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void updatePrenotazioneAccompagnatore(@PathVariable("date") String date,
                          @RequestBody PrenotazioneDTONew prenotazioneDTO, HttpServletResponse response, HttpServletRequest req) throws IOException {

    String[] dataPieces = date.split("-");
    LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

    idPrenotazione iP = idPrenotazione.builder()
      .data(data)
      .id_bambino(prenotazioneDTO.getId_bambino())
      .verso(prenotazioneDTO.getVerso())
      .build();

    Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
    if (p.isPresent()) {
      List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.get().getCorsa().getLinea().getNome());
      if (accompagnatori != null) {
        for (Utente u : accompagnatori) {
          if (u.getUserName().compareTo(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))) == 0) {
            if (p.get().isPresente() == true)
              p.get().setPresente(false);
            else
              p.get().setPresente(true);
            prenotazioneService.save(p.get());

            // for (Utente user : accompagnatori)
              response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/modificata/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));

            return;
          }
        }
      }
      throw new BadRequestException("Errore: accompagnatore non autorizzato poichè non è accompagnatore della linea");
    }

    throw new BadRequestException("errore nella modifica ");
  }

    @PostMapping(path = "/utility/reservations/{nome_linea}/{date}")
    public @ResponseBody
    IdPrenotazioneDTO postNuovaPrenotazione(@PathVariable("nome_linea") String nomeLinea,
                                            @PathVariable("date") String date,
                                            @RequestBody PrenotazioneDTONew prenotazioneDTO,
                                            HttpServletResponse response, HttpServletRequest req) throws IOException {


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

                List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
                if (accompagnatori != null) {
                    response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/modificato/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
                }
                return IdPrenotazioneDTO.builder().id(iP.toString()).build();
                //}
            } else {
                // INSERT
                boolean presente = false;
                Prenotazione p = Prenotazione.builder()
                        .fermata(f.get())
                        .presente(presente)
                        .corsa(corsaService.getCorsa(f.get().getLinea().getId(), data, prenotazioneDTO.getVerso()))
                        .id(iP)
                        .build();

                prenotazioneService.save(p);
                f.get().getPrenotazioni().add(p);


                List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
                if (accompagnatori != null) {
                    response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/effettuato/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
                }

                return IdPrenotazioneDTO.builder().id(iP.toString()).build();
            }
        }
        throw new NotFoundException("errore nella prenotazione");
    }

    @PutMapping(path = "/utility/reservations/{date}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void updatePrenotazione(@PathVariable("date") String date,
                            @RequestBody PrenotazioneDTONew prenotazioneDTO, HttpServletResponse response, HttpServletRequest req) throws IOException {


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

            List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.get().getCorsa().getLinea().getNome());
            if (accompagnatori != null) {
                // for (Utente user : accompagnatori)
                    response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/modificata/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
            }

            return;
        }

        throw new BadRequestException("errore nella modifica ");
    }


    @DeleteMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void deletePrenotazione(@PathVariable("nome_linea") String nomeLinea,
                            @PathVariable("date") String date,
                            @PathVariable("reservation_id") String res_id, HttpServletRequest req, HttpServletResponse response) throws IOException {


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
            response.sendRedirect("/notifyP/" + iP.getId_bambino() + "_" + iP.getData() + "_" + iP.getVerso() + "/cancellato/" + jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
            return ;
        }
        throw new BadRequestException("errore nella cancellazione");
    }


    @GetMapping("/notifyP/{id}/{action}/{username}")
    public void sendPostPrenotazione(@PathVariable("id") String id_prenotazione,
                                 @PathVariable("action") String azione,
                                 @PathVariable("username") String username){

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

                                           // Push notifications to front-end

                        List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
                        if (accompagnatori != null) {
                          Bambino b=bambinoService.getBambinoById(iP.getId_bambino());
                          if(b!=null){
                            notifications.setMsg("Il genitore " + utente.getUserName() + " ha " + azione + " la prenotazione");
                            notifications.setData(data.toString());
                            notifications.setVerso(pieces[2]);
                            notifications.setUtente(b.getNome()+" "+b.getCognome());
                            notifications.setLinea(p.getCorsa().getLinea().getNome());
                            notifications.setTipo(3);
                          }
                            for (Utente user : accompagnatori) {
                              if (!contatori.containsKey(user.getUserName())) {
                                contatori.put(user.getUserName(), 0);
                              }
                              Integer x = contatori.get(user.getUserName());
                              contatori.put(user.getUserName(), x + 1);
                              notifications.setCount(contatori.get(user.getUserName()));
                              template.convertAndSendToUser(user.getUserName(), "/queue/reply", notifications);
                            }
                                return;
                        }
                        throw new NotFoundException("nessun accompagnatore trovato");

                //throw new NotFoundException("bambino non trovato!");
                }
                throw new NotFoundException("turno non trovato no message!");
                // Increment Notification by one


                //System.out.println("Notifications successfully sent to Angular");

                //return "Notifications successfully sent to Angular !"
        }
        throw new NotFoundException("utente non trovato");

    }

    @PutMapping("/notifyP/{id}/{action}/{username}")
    public void sendPutPrenotazione(@PathVariable("id") String id_prenotazione,
                                     @PathVariable("action") String azione,
                                     @PathVariable("username") String username){

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

                    List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
                    if (accompagnatori != null) {
                      Bambino b=bambinoService.getBambinoById(iP.getId_bambino());
                      if(b!=null){
                        notifications.setMsg("Il genitore " + utente.getUserName() + " ha " + azione + " la prenotazione");
                        notifications.setData(data.toString());
                        notifications.setVerso(pieces[2]);
                        notifications.setUtente(b.getNome()+" "+b.getCognome());
                        notifications.setLinea(p.getCorsa().getLinea().getNome());
                        notifications.setTipo(3);
                      }
                      for (Utente user : accompagnatori) {
                          if (!contatori.containsKey(user.getUserName())) {
                            contatori.put(user.getUserName(), 0);
                          }
                          Integer x = contatori.get(user.getUserName());
                          contatori.put(user.getUserName(), x + 1);
                          notifications.setCount(contatori.get(user.getUserName()));
                          template.convertAndSendToUser(user.getUserName(), "/queue/reply", notifications);
                        }
                        return;
                    }
                    throw new NotFoundException("nessun accompagnatore trovato");
            }
            throw new NotFoundException("turno non trovato no message!");
            // Increment Notification by one


            //System.out.println("Notifications successfully sent to Angular");

            //return "Notifications successfully sent to Angular !"
        }
        throw new NotFoundException("utente non trovato");

    }

    @DeleteMapping("/notifyP/{id}/{action}/{username}")
    public void sendDeletePrenotazione(@PathVariable("id") String id_prenotazione,
                                     @PathVariable("action") String azione,
                                     @PathVariable("username") String username){

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
                prenotazioneService.deleteOne(p);

                    List<Utente> accompagnatori = utenteRuoloService.getAccompagnatoreByLinea(p.getCorsa().getLinea().getNome());
                    if (accompagnatori != null) {
                      Bambino b=bambinoService.getBambinoById(iP.getId_bambino());
                      if(b!=null){
                        notifications.setMsg("Il genitore " + utente.getUserName() + " ha " + azione + "la prenotazione");
                        notifications.setData(data.toString());
                        notifications.setVerso(pieces[2]);
                        notifications.setUtente(b.getNome()+" "+b.getCognome());
                        notifications.setLinea(p.getCorsa().getLinea().getNome());
                        notifications.setTipo(3);
                      }
                        for (Utente user : accompagnatori) {
                          if (!contatori.containsKey(user.getUserName())) {
                            contatori.put(user.getUserName(), 0);
                          }
                          Integer x = contatori.get(user.getUserName());
                          contatori.put(user.getUserName(), x + 1);
                          notifications.setCount(contatori.get(user.getUserName()));
                          template.convertAndSendToUser(user.getUserName(), "/queue/reply", notifications);
                        }
                         return;
                    }
                    throw new NotFoundException("nessun accompagnatore trovato");

            }
            throw new NotFoundException("turno non trovato no message!");
            // Increment Notification by one


            //System.out.println("Notifications successfully sent to Angular");

            //return "Notifications successfully sent to Angular !"
        }
        throw new NotFoundException("utente non trovato");

    }
}
