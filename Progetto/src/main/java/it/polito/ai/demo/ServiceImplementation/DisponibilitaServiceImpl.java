package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.Disponibilita;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Repository.DisponibilitaRepository;
import it.polito.ai.demo.Service.DisponibilitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisponibilitaServiceImpl implements DisponibilitaService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DisponibilitaRepository disponibilitaRepository;

    public void save(Disponibilita d) {
        em.persist(d);
    }

    public List<DisponibilitaGetDTO> getDisponibilitaByCorsa(int id) {

        List<Disponibilita> disponibilita = disponibilitaRepository.findByCorsa(id);

        List<DisponibilitaGetDTO> disp = new ArrayList<>();
        for (Disponibilita d : disponibilita) {
            DisponibilitaGetDTO d2 = DisponibilitaGetDTO.builder()
                    .username(d.getId().getUtente().getUserName())
                    .data(d.getId().getCorsa().getData().toString())
                    .linea(d.getId().getCorsa().getLinea().getNome())
                    .verso(d.getId().getCorsa().getVerso())
                    .build();
            disp.add(d2);
        }

        return disp;
    }

  public List<Disponibilita> getDisponibilitaByData(LocalDate data){
      List<Disponibilita> disponibilita = disponibilitaRepository.findByData(data);
      return disponibilita;
  }

  public void deleteOne(Disponibilita d){
    disponibilitaRepository.delete(d);
  }

  public List<Disponibilita> getDisponibilitaByUserDataVerso(Utente utente, LocalDate data, String verso){
    List<Disponibilita> disponibilita = disponibilitaRepository.findByUserDataVerso(utente,data,verso);
    return disponibilita;
  }
}
