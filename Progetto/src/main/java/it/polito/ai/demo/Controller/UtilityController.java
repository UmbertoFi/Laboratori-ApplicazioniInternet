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

import javax.persistence.criteria.CriteriaBuilder;
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
        children = new ArrayList<>();
      }

      return children;
    }
    throw new BadRequestException("username non valido!");

  }



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

  @PostMapping(path = "/utility/available/{username}")
  public @ResponseBody
  void postNuovaPrenotazione(@PathVariable("username") String username,
                             @RequestBody DisponibilitaDTO disponibilitaDTO) {

    Utente u = userService.getUserById(username);
    if (u != null) {
      String[] pieces = disponibilitaDTO.getData().split("-");
      LocalDate date = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

      Linea l = lineaService.getLinea(disponibilitaDTO.getLinea());
      String verso;
      if (l != null) {
        if (disponibilitaDTO.getVerso().compareTo("Andata") == 0) {
          verso = "A";
        } else {
          verso = "R";
        }

        Corsa c = corsaService.getCorsa(l.getId(), date, verso);
        if (c != null) {

          if(date.equals(LocalDate.now())) {
            String oraFermata = "23:59:00";

            for (Fermata f : c.getLinea().getFermate()) {
              if (verso.equals("A")) {
                if (f.getOra_andata().compareTo(oraFermata) < 0)
                  oraFermata = f.getOra_andata();
              } else {
                if (f.getOra_ritorno().compareTo(oraFermata) < 0)
                  oraFermata = f.getOra_ritorno();
              }
            }

            if (oraFermata.compareTo(java.time.LocalTime.now().toString()) < 0) {
              throw new BadRequestException("Disponibilità non accettata perchè corsa già partita");
            }
          }

          idTurno id = idTurno.builder().utente(u).data(c.getData()).verso(c.getVerso()).build();
          Optional<Turno> t = turnoService.getTurnoById(id);

          if (t.isPresent()) {
            throw new BadRequestException("Disponibilità non accettata perchè già assegnato in un turno consolidato per quella data e verso");
          }

          Disponibilita d = Disponibilita.builder()
            .id(idDisponibilita.builder().corsa(c).utente(u).build())
            .build();

          disponibilitaService.save(d);

          return;
        }
        throw new BadRequestException("corsa non esistente");
      }
      throw new BadRequestException("linea non esistente");
    }
    throw new BadRequestException("utente non esistente");

  }

  @PutMapping(path = "/utility/modificaRuolo")
  @ResponseStatus(HttpStatus.OK)
  public void modifyRole(HttpServletRequest req, @RequestBody ModificaRuoloDTO modificaRuoloDTO) {

    String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
    List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));


    if (ru.contains("system-admin")) {
      if (modificaRuoloDTO.getAzione().compareTo("Promuovi") == 0) {


        Utente u = userService.getUserById(modificaRuoloDTO.getUsername());
        if (u != null) {
          UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");


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
          throw new NotFoundException(modificaRuoloDTO.getUsername() + " già admin per la linea " + modificaRuoloDTO.getLinea());
        }
        throw new NotFoundException("errore nell' utente(system-admin)");
      } else {
        UtenteRuolo utenteRuolo = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");
        if (utenteRuolo != null) {
          utenteRuoloService.deleteOne(utenteRuolo);
          return;
        }
        throw new NotFoundException(modificaRuoloDTO.getUsername() + " non è ancora admin per la linea " + modificaRuoloDTO.getLinea());
      }
    } else if (ru.contains("admin")) {

      List<String> linee = jwtTokenProvider.getLinee(jwtTokenProvider.resolveToken(req));
      if (linee.contains(modificaRuoloDTO.getLinea()) == true) {

        if (modificaRuoloDTO.getAzione().compareTo("Promuovi") == 0) {

          Utente u2 = userService.getUserById(modificaRuoloDTO.getUsername());

          if (u2 != null) {
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
            throw new NotFoundException(modificaRuoloDTO.getUsername() + " già admin per la linea " + modificaRuoloDTO.getLinea());
          }
          throw new NotFoundException("errore nell' utente(admin)");
        } else {
          UtenteRuolo utenteRuolo2 = utenteRuoloService.getUtenteRuolo(modificaRuoloDTO.getUsername(), modificaRuoloDTO.getLinea(), "admin");
          if (utenteRuolo2 != null) {
            utenteRuoloService.deleteOne(utenteRuolo2);
            return;
          }
          throw new NotFoundException(modificaRuoloDTO.getUsername() + " non ancora admin per la linea " + modificaRuoloDTO.getLinea());
        }

      }
    }
    throw new UnauthorizedException("non autorizzato");

  }

  NotificationController notificationController;

  @GetMapping(path = "/utility/disponibilita/{linea}/{data}/{verso}")
  public @ResponseBody
  List<DisponibilitaGetDTO> postDisponibilita(@PathVariable("linea") String nomeLinea,
                                              @PathVariable("data") String date,
                                              @PathVariable("verso") String verso) {


    String[] pieces = date.split("-");
    LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
    String v;
    if (verso.compareTo("Andata") == 0)
      v = "A";
    else
      v = "R";

    Corsa c = corsaService.getCorsa(lineaService.getLinea(nomeLinea).getId(), data, v);
    if (c != null) {
      List<DisponibilitaGetDTO> disponibilita = disponibilitaService.getDisponibilitaByCorsa(c.getId());
      if (!disponibilita.isEmpty()) {
        return disponibilita;
      }
      throw new BadRequestException("Nessuna disponibilità per la corsa scelta!");
    }
    throw new BadRequestException("Corsa non trovata!");
  }


  @GetMapping(path = "/utility/ruoli")
  public @ResponseBody
  List<RuoloDTO> getAllCorse(HttpServletRequest req) {

    List<RuoloDTO> ruoli = new ArrayList<>();

    List<String> ru = jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req));

    for (String s : ru) {
      ruoli.add(RuoloDTO.builder().ruolo(s).build());
    }
    return ruoli;
  }


  @DeleteMapping(path = "/utility/pulisciDatabase/{date}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void pulisciDatabase(@PathVariable("date") String date) {

    String[] dataPieces = date.split("-");
    LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

    List<Disponibilita> disponibilita = disponibilitaService.getDisponibilitaByData(data);
    for (Disponibilita d : disponibilita)
      disponibilitaService.deleteOne(d);

    List<Turno> turni = turnoService.getTurniByData(data);
    for (Turno t : turni)
      turnoService.deleteOne(t);

  }


  @GetMapping(path = "/utility/checkChildren/{id_bambino}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void controllaFiglio(@PathVariable("id_bambino") int id, HttpServletRequest req) {
    String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
    Utente u = userService.getUserById(username);
    List<BambinoDTO> figli = bambinoService.getFigli(u);
    for (BambinoDTO b : figli) {
      if (b.getId_bambino() == id)
        return;
    }
    throw new BadRequestException("Errore: Il genitore non può cancellare la prenotazione di un figlio non suo");
  }


  @GetMapping("utility/turni")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<DisponibilitaGetDTO> getTurni(HttpServletRequest req) {


    String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
    Utente u = userService.getUserById(username);
    if (u == null)
      throw new BadRequestException("utente non esistente ");

    if (!jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)).contains("accompagnatore"))
      throw new UnauthorizedException("utente non è abilitato come accompagnatore");

    List<DisponibilitaGetDTO> turni = turnoService.getProxTurni(u);
    return turni;
  }


  @GetMapping(path = "/utility/adminlines/{utente}")
  public @ResponseBody
  List<String> getAllLinea(@PathVariable("utente") String username) throws Exception {
    List<String> nomiLinee = utenteRuoloService.getLinesbyUser(username);
    for (String linea : nomiLinee) {
      if (linea.compareTo("*") == 0) {
        Iterable<Linea> allLinee = lineaService.getLines();
        List<String> allNomiLinee = new ArrayList<>();
        for (Linea l : allLinee)
          allNomiLinee.add(l.getNome());
        return allNomiLinee;
      }
    }
    return nomiLinee;
  }

  @GetMapping(path = "/lines")
  public @ResponseBody
  List<NomeLineaDTO> getAllLinea() throws Exception {
    Iterable<Linea> linee = lineaService.getLines();
    List<NomeLineaDTO> nomiLinee = new ArrayList<>();
    for (Linea linea : linee)
      nomiLinee.add(linea.convertToNomeLineaDTO());
    return nomiLinee;
  }


}
