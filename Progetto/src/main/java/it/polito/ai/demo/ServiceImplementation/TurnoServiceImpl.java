package it.polito.ai.demo.ServiceImplementation;

import it.polito.ai.demo.DTO.DisponibilitaGetDTO;
import it.polito.ai.demo.Entity.Turno;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.idTurno;
import it.polito.ai.demo.Repository.TurnoRepository;
import it.polito.ai.demo.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    TurnoRepository turnoRepository;

    public void save(Turno t) {
        em.persist(t);
    }

    public Optional<Turno> getTurnoById(idTurno id) {
        return turnoRepository.findById(id); }

  @Override
  public List<DisponibilitaGetDTO> getProxTurni(Utente user) {
      List<Turno> turni=turnoRepository.getTurniProx(user);
      if(turni==null)
        return null;
      List<DisponibilitaGetDTO> turniDTO=new ArrayList<>();

      for (Turno t: turni.stream().filter(x->x.getId().getData().compareTo(LocalDate.now())>=0).collect(Collectors.toList())){
        String data=new String(t.getId().getData().getDayOfMonth()+"-"+t.getId().getData().getMonthValue()+"-"+t.getId().getData().getYear());
        DisponibilitaGetDTO d=DisponibilitaGetDTO.builder().data(data)
                              .linea(t.getLinea().getNome())
                              .username(t.getId().getUtente().getUserName())
                              .verso(t.getId().getVerso())
                              .build();
        turniDTO.add(d);
      }

      return turniDTO;
  }

  @Override
  public void deleteTurno(idTurno id) {

      turnoRepository.deleteById(id);
  }


}
