package it.polito.ai.lab2.demo;

import it.polito.ai.lab2.demo.DTO.FermataDTO;
import it.polito.ai.lab2.demo.DTO.LineaDTO;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Service.FermataService;
import it.polito.ai.lab2.demo.Service.LineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "demo") // This means URL's start with /demo (after Application path)
public class Controller {
    @Autowired
    private LineaService lineaService;
    @Autowired
    private FermataService fermataService;


    @GetMapping(path="/lines")
    public @ResponseBody
    List<LineaDTO> getAllLinea() {
        Iterable<Linea> linee = lineaService.getLines();
        List<LineaDTO> nomiLinee = new ArrayList<>();
        for (Linea linea : linee)
            nomiLinee.add(linea.convertToDTO());
        return nomiLinee;
    }
}
