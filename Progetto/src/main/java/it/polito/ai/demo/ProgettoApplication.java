package it.polito.ai.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ai.demo.DTO.*;
import it.polito.ai.demo.Entity.*;
import it.polito.ai.demo.Repository.CorsaRepository;
import it.polito.ai.demo.Repository.FermataRepository;
import it.polito.ai.demo.Repository.LineaRepository;
import it.polito.ai.demo.Service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.persistence.EntityManagerFactory;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@SpringBootApplication
@EnableTransactionManagement
public class ProgettoApplication {

  public String generateUUID() {
    Random r = new Random();
    StringBuilder generatedString = new StringBuilder();
    for (int i = 0; i < 5; i++)
      generatedString.append(r.nextInt());

    return generatedString.toString();
  }

    public static void main(String[] args) {
        SpringApplication.run(ProgettoApplication.class, args);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    @Bean
    CommandLineRunner runner(CorsaService cs, UtenteRuoloService urs, LineaService ls, UserService us, PrenotazioneService ps, LineaRepository lr, FermataRepository fs, CorsaRepository cr,  EmailService email) {
        return argss -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ClassLoader cl = this.getClass().getClassLoader();
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
                Resource[] resources = resolver.getResources("classpath*:/json/*.json");
                for (Resource r : resources) {
                    InputStream resource = new ClassPathResource("json/"+r.getFilename()).getInputStream();
                    LineaDTO linea = mapper.readValue(resource, LineaDTO.class);
                    ls.save(linea);
                    Utente u = us.getUserById(linea.getAmministratore());
                    if(u==null){
                        String UUID=generateUUID();
                         u = Utente.builder()
                        .UserName(linea.getAmministratore())
                        .Password("{bcrypt}$2a$10$xDV2akL7gB9eH.v8LPmNn.qVdOcDeoHSReUX0IYUFMLhxzbiVaRgO")
                        .token(UUID)
                        .expiredToken(new Date())
                        .enabled(false)
                        .expiredAccount(true)//logica inversa
                        .expiredcredential(true)//logica inversa
                        .locked(true)//logica inversa
                        .build();


                         idRuolo id = idRuolo.builder()
                        .utente(u)
                        .ruolo("admin")
                        .NomeLinea(linea.getNome())
                        .build();

                      UtenteRuolo ur = UtenteRuolo.builder()
                        .id(id)
                        .build();

                      us.save(u);
                      urs.save(ur);

                      id.setRuolo("user");
                      id.setNomeLinea("*");
                      ur.setId(id);
                      urs.save(ur);


                      String body = "Gentilissimo, confermi di esserti registrato al servizio?, se sì clicca il seguente link per confermare la registrazione http://localhost:8080/confirm/" + UUID;
                      //email.sendSimpleMessage(u.getUserName(), "Benvenuto!", body);

                    }
                    LocalDate today = LocalDate.now();
                    for(int i=0; i<365; i++){
                        LocalDate day = today.plusDays(i);
                        Corsa c1 = Corsa.builder().data(day).linea(linea.convertToEntity()).verso("A").build();
                        Corsa c2 = Corsa.builder().data(day).linea(linea.convertToEntity()).verso("R").build();
                        cs.save(c1);
                        cs.save(c2);
                    }
                }
                ListaUtentiDTO utenti = mapper.readValue(new ClassPathResource("json_new/utenti.json").getInputStream(), ListaUtentiDTO.class);
                int i=0;
                for(UtenteDTO u : utenti.getUtenti()) {


                    UtenteRuolo ur;

                    Utente utente=u.convertToEntity();
                    idRuolo id = idRuolo.builder()
                            .utente(utente)
                            .ruolo("user")
                            .NomeLinea("*")
                            .build();

                    ur = UtenteRuolo.builder()
                            .id(id)
                            .build();

                    us.save(utente);
                    urs.save(ur);

                    String ruolo;
                    String nomelinea;
                    if (urs.getByRuoloSystemAdmin() == false) {
                        ruolo = "system-admin";
                        nomelinea="*";
                    } else {
                        int resto=i%2;
                        if(resto==1){
                            ruolo="accompagnatore";
                            nomelinea="Santa_Rita-Politecnico";

                        }
                        else{
                            i++;
                            continue;
                        }
                    }
                    idRuolo id2 = idRuolo.builder()
                            .utente(utente)
                            .ruolo(ruolo)
                            .NomeLinea(nomelinea)
                            .build();

                    ur = UtenteRuolo.builder()
                            .id(id2)
                            .build();

                    urs.save(ur);

                    i++;
                }
                ListaPrenotatiDTO prenotazioni = mapper.readValue(new ClassPathResource("json_new/prenotazioni.json").getInputStream(), ListaPrenotatiDTO.class);
                for(PrenotatoDTO p:prenotazioni.getPrenotazioni()) {
                    Prenotazione pren = p.convertToEntity(fs,cr);
                    if(pren.getCorsa()!=null) {
                        ps.save(p.convertToEntity(fs, cr));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
