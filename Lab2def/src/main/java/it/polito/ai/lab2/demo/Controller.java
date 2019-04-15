package it.polito.ai.lab2.demo;

import it.polito.ai.lab2.demo.DTO.*;
import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Entity.Prenotazione;
import it.polito.ai.lab2.demo.Entity.idPrenotazione;
import it.polito.ai.lab2.demo.Service.FermataService;
import it.polito.ai.lab2.demo.Service.LineaService;
import it.polito.ai.lab2.demo.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path = "demo") // This means URL's start with /demo (after Application path)
public class Controller {

    @Autowired
    private LineaService lineaService;

    @Autowired
    private FermataService fermataService;

    @Autowired
    private PrenotazioneService prenotazioneService;




    @GetMapping(path="/lines")
    public @ResponseBody
    List<NomeLineaDTO> getAllLinea() throws Exception{
        Iterable<Linea> linee = lineaService.getLines();
        List<NomeLineaDTO> nomiLinee = new ArrayList<>();
        for (Linea linea : linee)
            nomiLinee.add(linea.convertToNomeLineaDTO());
        return nomiLinee;
    }

    @GetMapping(path = "/lines/{nome_linea}")
    public @ResponseBody
    List<List<DettagliLineaDTO>> getDettagliLinea(@PathVariable("nome_linea") String nomeLinea) {

        /*Linea linea = lineaRepository.findByNome(nomeLinea);
        List<Fermata> fermate = fermataRepository.findByLinea(linea);*/
        List<Fermata> fermate =fermataService.getFermateList(nomeLinea);
        List<DettagliLineaDTO> fermateAndata = new ArrayList<>();
        List<DettagliLineaDTO> fermateRitorno = new ArrayList<>();
        for(Fermata f : fermate){
            DettagliLineaDTO fDTO = f.convertToDettagliLineaDTO("andata");
            fermateAndata.add(fDTO);
        }
        Collections.reverse(fermate);
        for(Fermata f : fermate){
            DettagliLineaDTO fDTO2 = f.convertToDettagliLineaDTO("ritorno");
            fermateRitorno.add(fDTO2);
        }

        List<List<DettagliLineaDTO>> dettagliLinea = new ArrayList<>();
        dettagliLinea.add(fermateAndata);
        dettagliLinea.add(fermateRitorno);

        return dettagliLinea;
    }
/*
    @GetMapping(path = "/add1")
    public @ResponseBody
    String addPrenotazioni() {
        Prenotazione p = new Prenotazione();
        idPrenotazione iP = new idPrenotazione();
        iP.setData(LocalDate.of(2001, 01, 01));
        iP.setPersona("Barberinho");
        iP.setVerso("A");
        p.setId(iP);
        Optional<Fermata> f = fermataRepository.findById(4);
        if (f.isPresent())
            p.setFermata(f.get());

        Prenotazione p1 = new Prenotazione();
        idPrenotazione iP1 = new idPrenotazione();
        iP1.setData(LocalDate.of(2002, 02, 02));
        iP1.setPersona("Mario Rossi");
        iP1.setVerso("A");
        p1.setId(iP1);
        Optional<Fermata> f1 = fermataRepository.findById(5);
        if (f1.isPresent())
            p1.setFermata(f1.get());

        Prenotazione p2 = new Prenotazione();
        idPrenotazione iP2 = new idPrenotazione();
        iP2.setData(LocalDate.of(2002, 02, 02));
        iP2.setPersona("Malnati Greit");
        iP2.setVerso("R");
        p2.setId(iP2);
        Optional<Fermata> f2 = fermataRepository.findById(5);
        if (f2.isPresent())
            p2.setFermata(f2.get());


        prenotazioneRepository.save(p);
        prenotazioneRepository.save(p1);
        prenotazioneRepository.save(p2);
        return "saved";
    }
*/
    @GetMapping(path = "/reservations/{nome_linea}/{date}")
    public @ResponseBody
    List<List<DettagliLineaPersoneDTO>> getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                        @PathVariable("date") String date) {


        String[] pieces = date.split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
        //LocalDate data = LocalDate.of(2002, 02, 02);

        /*Linea linea = lineaRepository.findByNome(nomeLinea);
        List<Fermata> fermate = fermataRepository.findByLinea(linea);*/
        List<Fermata> fermate =fermataService.getFermateList(nomeLinea);

        List<Prenotazione> prenotazioni_A = new ArrayList<Prenotazione>();
        List<Prenotazione> prenotazioni_R = new ArrayList<Prenotazione>();

        List<DettagliLineaPersoneDTO> fermateAndata = new ArrayList<>();
        List<DettagliLineaPersoneDTO> fermateRitorno = new ArrayList<>();


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
            List<String> persone = new ArrayList<>();
            for (Prenotazione p1 : prenotazioni_A) {
                if (p1.getFermata().getId() == f2.getId()) {
                    persone.add(p1.getId().getPersona());
                }
            }
            DettagliLineaPersoneDTO dlp = f2.convertToDettagliLineaPersoneDTO(persone);
            fermateAndata.add(dlp);
        }

        Collections.reverse(fermate);
        for (Fermata f2 : fermate) {
            List<String> persone = new ArrayList<>();
            for (Prenotazione p1 : prenotazioni_R) {
                if (p1.getFermata().getId() == f2.getId()) {
                    persone.add(p1.getId().getPersona());
                }
            }
            DettagliLineaPersoneDTO dlp2 = f2.convertToDettagliLineaPersoneDTO(persone);
            fermateRitorno.add(dlp2);
        }



        List<List<DettagliLineaPersoneDTO>> dettagliLineaPersone = new ArrayList<>();
        dettagliLineaPersone.add(fermateAndata);
        dettagliLineaPersone.add(fermateRitorno);

        return dettagliLineaPersone;

    }

    @GetMapping(path = "/reservations/{nome_linea}/{date}/{id_prenotazione}")
    public @ResponseBody
    PrenotazioneDTO getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                                 @PathVariable("date") String date,
                                                 @PathVariable("id_prenotazione") String res_id) {

        /*Linea linea = lineaRepository.findByNome(nomeLinea);
        List<Fermata> fermate = fermataRepository.findByLinea(linea);*/
        List<Fermata> fermate =fermataService.getFermateList(nomeLinea);
        String[] pieces = res_id.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data =LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        idPrenotazione iP = idPrenotazione.builder()
                .data(data)
                .persona(pieces[0])
                .verso(pieces[2])
                .build();

        Optional<Prenotazione> pren = prenotazioneService.getPrenotazione(iP);


        if (pren.isPresent() && date.equals(pieces[1])) {
            PrenotazioneDTO p = pren.get().convertToDTO();
            return p;
        }

        return null;
    }


    @PostMapping(path = "/reservations/{nome_linea}/{date}")
    public @ResponseBody
    String postNuovaPrenotazione(@PathVariable("nome_linea") String nomeLinea,
                                 @PathVariable("date") String date,
                                 @RequestBody PostDTO postDTO) {


        Optional<Fermata> f = fermataService.getFermata(Integer.parseInt(postDTO.getFermata()));
        if (f.isPresent()) {

            String[] pieces = date.split("-");
            LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

            idPrenotazione iP= idPrenotazione.builder()
                    .data(data)
                    .persona(postDTO.getPersona())
                    .verso(postDTO.getVerso())
                    .build();

            Prenotazione p = Prenotazione.builder()
                    .fermata(f.get())
                    .id(iP)
                    .build();

            prenotazioneService.save(p);
            f.get().getPrenotazioni().add(p);
            return iP.toString();
        }
        return "errore";
    }

    @PutMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
    public @ResponseBody
    String updatePrenotazione(@PathVariable("nome_linea") String nomeLinea,
                              @PathVariable("date") String date,
                              @PathVariable("reservation_id") String res_id,
                              @RequestBody PostDTO postDTO) {


        String[] pieces = res_id.split("_");
        String[] dataPieces = pieces[1].split("-");
        LocalDate data=LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        idPrenotazione iP = idPrenotazione.builder()
                            .data(data)
                            .persona(pieces[0])
                            .verso(pieces[2])
                            .build();

        //Optional<Fermata> f = fermataRepository.findById(Integer.parseInt(postDTO.getFermata()));
        //Optional<Prenotazione> p = prenotazioneRepository.findById(iP);
        Optional<Fermata> f=fermataService.getFermata(Integer.parseInt(postDTO.getFermata()));
        Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
        if(p.isPresent()){
            if(f.isPresent()) {
                p.get().setFermata(f.get());
                prenotazioneService.save(p.get());
                return "aggiornamento fatto!";
            }
        }

        return "errore!";
    }

    @DeleteMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
    public @ResponseBody String deletePrenotazione(@PathVariable("nome_linea") String nomeLinea,
                                                   @PathVariable("date") String date,
                                                   @PathVariable("reservation_id") String res_id) {


        String[] pieces = res_id.split("_");
        String[] dataPieces = pieces[1].split("-");
        LocalDate data=LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        idPrenotazione iP = idPrenotazione.builder()
                            .data(data)
                            .persona(pieces[0])
                            .verso(pieces[2])
                            .build();

        Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
        if (p.isPresent()) {
            prenotazioneService.deleteOne(p.get());
            return "cancellazione fatta!";
        }

        return "errore!";
    }
}
