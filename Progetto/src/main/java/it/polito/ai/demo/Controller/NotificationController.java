package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.DTO.NotificaDTO;
import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.idTurno;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Service.LineaService;
import it.polito.ai.demo.Service.TurnoService;
import it.polito.ai.demo.Service.UserService;
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
import java.util.Date;
import java.util.Optional;

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

    // Initialize Notifications
    private NotificaDTO notifications = NotificaDTO.builder().count(0).msg("prova").build();

    @GetMapping("/notify/{username}")
    public void sendTurno(@PathVariable("username") String email) {

        //System.out.println("notify ci sono");
        // Increment Notification by one
        notifications.increment();
        notifications.setMsg("");
        // Push notifications to front-end
        template.convertAndSendToUser(email,"/queue/reply", notifications);

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
            //notificationController.getNotification();
            System.out.println("redirect");
            response.sendRedirect("/notify/"+id.getUtente().getUserName());

            return;
        }
        throw new NotFoundException("Turno già presente!");

    }
}
