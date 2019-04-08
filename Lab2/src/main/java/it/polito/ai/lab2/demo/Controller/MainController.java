package it.polito.ai.lab2.demo.Controller;

import it.polito.ai.lab2.demo.Repository.FermataRepository;
import it.polito.ai.lab2.demo.Repository.LineaRepository;
import it.polito.ai.lab2.demo.Repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /* @GetMapping(path="/lines")
    public @ResponseBody List<String> getAllLinea() {
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
    } */


}
