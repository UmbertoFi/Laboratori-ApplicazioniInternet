package it.polito.ai.lab2.demo.Controller;

import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;
import it.polito.ai.lab2.demo.Repository.FermataRepository;
import it.polito.ai.lab2.demo.Repository.LineaRepository;
import it.polito.ai.lab2.demo.Repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping(path = "demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    private LineaRepository lineaRepository;
    @Autowired
    private FermataRepository fermataRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;


    /* ------------------------------------------- */

    /* @GetMapping(path="/add1")
    public @ResponseBody String addLinea1(){
        Linea l = new Linea();
        l.setId(1);
        l.setNome("Linea1");
        l.setAmministratore("amm1");
        lineaRepository.save(l);
        return "saved";
    }
    @GetMapping(path="/add2")
    public @ResponseBody String addLinea2(){
        Linea l = new Linea();
        l.setId(2);
        l.setNome("Linea2");
        l.setAmministratore("amm2");
        lineaRepository.save(l);
        return "saved";
    }
    @GetMapping(path="/add3")
    public @ResponseBody String addFermata3(){
        Fermata f = new Fermata();
        f.setId(121);
        f.setLinea(lineaRepository.findByNome("Linea1"));
        f.setN_seq(1);
        f.setNome("ciaociao");
        f.setOra_andata("08:30");
        f.setOra_ritorno("13:00");
        fermataRepository.save(f);
        return "saved";
    }
    @GetMapping(path="/add4")
    public @ResponseBody String addFermata4(){
        Fermata f = new Fermata();
        f.setId(123);
        f.setLinea(lineaRepository.findByNome("Linea1"));
        f.setN_seq(2);
        f.setNome("ciaociao4");
        f.setOra_andata("08:40");
        f.setOra_ritorno("12:50");
        fermataRepository.save(f);
        return "saved";
    }
    @GetMapping(path="/add5")
    public @ResponseBody String addFermata5(){
        Fermata f = new Fermata();
        f.setId(125);
        f.setLinea(lineaRepository.findByNome("Linea1"));
        f.setN_seq(3);
        f.setNome("ciaociao5");
        f.setOra_andata("08:50");
        f.setOra_ritorno("12:40");
        fermataRepository.save(f);
        return "saved";
    } */

    /* ------------------------------------------- */

     @GetMapping(path="/lines")
    public @ResponseBody
     List<String> getAllLinea() {
        Iterable<Linea> linee = lineaRepository.findAll();
        List<String> nomiLinee = new ArrayList<>();
        for (Linea linea : linee)
            nomiLinee.add(linea.getNome());
        return nomiLinee;
    }

    @GetMapping(path="/lines/{nome_linea}")
    public @ResponseBody String getDettagliLinea(@PathVariable("nome_linea") String nomeLinea) {

        Linea linea = lineaRepository.findByNome(nomeLinea);
        List<Fermata> fermate = fermataRepository.findByLinea(linea);
        String dettagliAndata="Andata:<br>";
        String dettagliRitorno="<br>Ritorno:<br>";
        for(Fermata f : fermate)
            dettagliAndata += "----> " + f.getId()+" "+f.getNome()+" "+f.getOra_andata()+"<br>";
        Collections.reverse(fermate);
        for(Fermata f : fermate)
            dettagliRitorno += "----> " + f.getId()+" "+f.getNome()+" "+f.getOra_ritorno()+"<br>";
        return dettagliAndata+dettagliRitorno;
    }

    @GetMapping(path="/add1")
    public @ResponseBody String addPrenotazioni() {
        Prenotazione p = new Prenotazione();
        idPrenotazione iP = new idPrenotazione();
        iP.setData(LocalDate.of(2001, 01, 01));
        iP.setPersona("Barberinho");
        iP.setVerso("A");
        p.setId(iP);
        Optional<Fermata> f = fermataRepository.findById(319);
        if (f.isPresent())
            p.setFermata(f.get());

        Prenotazione p1 = new Prenotazione();
        idPrenotazione iP1 = new idPrenotazione();
        iP1.setData(LocalDate.of(2002, 02, 02));
        iP1.setPersona("Mario Rossi");
        iP1.setVerso("A");
        p1.setId(iP1);
        Optional<Fermata> f1 = fermataRepository.findById(323);
        if (f1.isPresent())
            p1.setFermata(f1.get());

        Prenotazione p2 = new Prenotazione();
        idPrenotazione iP2 = new idPrenotazione();
        iP2.setData(LocalDate.of(2002, 02, 02));
        iP2.setPersona("Malnati Greit");
        iP2.setVerso("R");
        p2.setId(iP2);
        Optional<Fermata> f2 = fermataRepository.findById(323);
        if (f2.isPresent())
            p2.setFermata(f2.get());


        prenotazioneRepository.save(p);
        prenotazioneRepository.save(p1);
        prenotazioneRepository.save(p2);
        return "saved";
    }

    @GetMapping(path="/reservations/{nome_linea}/{date}")
    public @ResponseBody String getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                                             @PathVariable("date")  String date) {
        {


            String[] pieces=date.split("-");
            LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
            //LocalDate data = LocalDate.of(2002, 02, 02);

            Linea linea = lineaRepository.findByNome(nomeLinea);
            List<Fermata> fermate = fermataRepository.findByLinea(linea);

            List<Prenotazione> prenotazioni_A = new ArrayList<Prenotazione>();
            List<Prenotazione> prenotazioni_R = new ArrayList<Prenotazione>();

            Iterable<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
            Map<Fermata, List<String>> fermata_persone_A = new HashMap<>();
            Map<Fermata, List<String>> fermata_persone_R = new HashMap<>();
            for (Prenotazione p : prenotazioni) {
                if (p.getId().getData().equals(data)) {
                    if (p.getId().getVerso().equals("A"))
                        prenotazioni_A.add(p);
                    else
                        prenotazioni_R.add(p);
                }
            }

            for (Fermata f2 : fermate) {
                List<String> persone = new ArrayList<>();
                for (Prenotazione p1 : prenotazioni_A) {
                    if (p1.getFermata().getId() == f2.getId()) {
                        persone.add(p1.getId().getPersona());
                    }
                }
                if (persone.isEmpty() == false)
                    fermata_persone_A.put(f2, persone);
            }

            for (Fermata f2 : fermate) {
                List<String> persone = new ArrayList<>();
                for (Prenotazione p1 : prenotazioni_R) {
                    if (p1.getFermata().getId() == f2.getId()) {
                        persone.add(p1.getId().getPersona());
                    }
                }
                if (persone.isEmpty() == false)
                    fermata_persone_R.put(f2, persone);
            }


            String res = "Fermata Andata: <br>";
            for (Fermata key : fermata_persone_A.keySet()) {
                res+=key.getId()+": <br>";
                for (List<String> pers : fermata_persone_A.values()) {
                    for (String s : pers) {
                        res+=s+" ";
                    }
                }
            }

            res+= "<br><br> Fermate ritorno: <br>";
            for (Fermata key : fermata_persone_A.keySet()) {
                res+=key.getId()+": <br>";
                for (List<String> pers : fermata_persone_R.values()) {
                    for (String s : pers) {
                        res+=s+" ";
                    }
                }
            }

            return res;

        }

    }

}

