package it.polito.ai.demo.Controller;

import it.polito.ai.demo.DTO.*;
import it.polito.ai.demo.Entity.Fermata;
import it.polito.ai.demo.Entity.Linea;
import it.polito.ai.demo.Entity.Prenotazione;
import it.polito.ai.demo.Entity.idPrenotazione;
import it.polito.ai.demo.Exception.BadRequestException;
import it.polito.ai.demo.Exception.NotFoundException;
import it.polito.ai.demo.Service.BambinoService;
import it.polito.ai.demo.Service.FermataService;
import it.polito.ai.demo.Service.LineaService;
import it.polito.ai.demo.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
//@RequestMapping(path = "demo")
public class Controller {

    @Autowired
    private LineaService lineaService;

    @Autowired
    private FermataService fermataService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private BambinoService bambinoService;


    @GetMapping(path = "/lines")
    public @ResponseBody
    List<NomeLineaDTO> getAllLinea() throws Exception {
        Iterable<Linea> linee = lineaService.getLines();
        List<NomeLineaDTO> nomiLinee = new ArrayList<>();
        for (Linea linea : linee)
            nomiLinee.add(linea.convertToNomeLineaDTO());
        return nomiLinee;
    }


    /* @GetMapping(path = "/lines/{nome_linea}")
    public @ResponseBody
    TabelloneLineaDTO getDettagliLinea(@PathVariable("nome_linea") String nomeLinea) {

        List<Fermata> fermate = fermataService.getFermateList(nomeLinea);
        TabelloneLineaDTO dettagliLinea = new TabelloneLineaDTO();

        for (Fermata f : fermate) {
            DettagliLineaDTO fDTO = f.convertToDettagliLineaDTO("andata");
            dettagliLinea.getFermateA().add(fDTO);
        }
        Collections.reverse(fermate);
        for (Fermata f : fermate) {
            DettagliLineaDTO fDTO2 = f.convertToDettagliLineaDTO("ritorno");
            dettagliLinea.getFermateR().add(fDTO2);
        }

        return dettagliLinea;
    } */


    /* @GetMapping(path = "/reservations/{nome_linea}/{date}")
    public @ResponseBody
    PasseggeriDTO getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                               @PathVariable("date") String date) {


        String[] pieces = date.split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));

        List<Fermata> fermate = fermataService.getFermateList(nomeLinea);

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
                    persone.add(bambinoService.getNome(p1.getId().getId_bambino()));
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
                    persone.add(bambinoService.getNome(p1.getId().getId_bambino()));
                }
            }
            DettagliLineaPersoneDTO dlp2 = f2.convertToDettagliLineaPersoneDTO(persone);
            fermateRitorno.add(dlp2);
        }


        PasseggeriDTO dettagliLineaPersone = new PasseggeriDTO();
        dettagliLineaPersone.setFermateA(fermateAndata);
        dettagliLineaPersone.setFermateR(fermateRitorno);

        return dettagliLineaPersone;

    } */


    /* @GetMapping(path = "/reservations/{nome_linea}/{date}/{id_prenotazione}")
    public @ResponseBody
    PrenotatoDTO getDettagliPrenotazioniLinea(@PathVariable("nome_linea") String nomeLinea,
                                              @PathVariable("date") String date,
                                              @PathVariable("id_prenotazione") String res_id) {


        List<Fermata> fermate = fermataService.getFermateList(nomeLinea);
        String[] pieces = res_id.split("_");


        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));
        idPrenotazione iP = idPrenotazione.builder()
                .data(data)
                .id_bambino(Integer.parseInt(pieces[0]))
                .verso(pieces[2])
                .build();

        Optional<Prenotazione> pren = prenotazioneService.getPrenotazione(iP);


        if (pren.isPresent() && date.equals(pieces[1])) {
            PrenotatoDTO p = pren.get().convertToDTO();
            return p;
        }

        return null;
    } */


    /* @PostMapping(path = "/reservations/{nome_linea}/{date}")
    public @ResponseBody
    IdPrenotazioneDTO postNuovaPrenotazione(@PathVariable("nome_linea") String nomeLinea,
                                 @PathVariable("date") String date,
                                 @RequestBody PrenotazioneDTO prenotazioneDTO) {


        Optional<Fermata> f = fermataService.getFermata(Integer.parseInt(prenotazioneDTO.getFermata()));
        if (f.isPresent()) {

            String[] pieces = date.split("-");
            LocalDate data = LocalDate.of(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
            LocalDate curr = LocalDate.now();

            idPrenotazione iP = idPrenotazione.builder()
                    .data(data)
                    .id_bambino(prenotazioneDTO.getId_bambino())
                    .verso(prenotazioneDTO.getVerso())
                    .build();

            boolean presente;
            if(data.compareTo(curr)==0){
                presente = true;
            } else {
                presente = false;
            }
            Prenotazione p = Prenotazione.builder()
                    .fermata(f.get())
                    .presente(presente)
                    .id(iP)
                    .build();

            prenotazioneService.save(p);
            f.get().getPrenotazioni().add(p);
            return IdPrenotazioneDTO.builder().id(iP.toString()).build();
        }
        throw new NotFoundException("errore nella prenotazione");
    } */

    /* @PutMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void updatePrenotazione(@PathVariable("nome_linea") String nomeLinea,
                              @PathVariable("date") String date,
                              @PathVariable("reservation_id") String res_id,
                              @RequestBody PrenotazioneDTO prenotazioneDTO) {


        String[] pieces = res_id.split("_");
        String[] dataPieces = pieces[1].split("-");
        LocalDate data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        idPrenotazione iP = idPrenotazione.builder()
                .data(data)
                .id_bambino(Integer.parseInt(pieces[0]))
                .verso(pieces[2])
                .build();


        Optional<Fermata> f = fermataService.getFermata(Integer.parseInt(prenotazioneDTO.getFermata()));
        Optional<Prenotazione> p = prenotazioneService.getPrenotazione(iP);
        if (p.isPresent()) {
            if (f.isPresent()) {
                p.get().setFermata(f.get());
                prenotazioneService.save(p.get());
                return;
            }
        }

        throw new BadRequestException("errore nella modifica ");
    } */

    /* @DeleteMapping(path = "/reservations/{nome_linea}/{date}/{reservation_id}")
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
    } */
}
