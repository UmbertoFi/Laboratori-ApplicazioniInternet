package com.example.demo;

import com.example.demo.DTO.*;
import com.example.demo.Repository.FermataRepository;
import com.example.demo.Service.LineaService;
import com.example.demo.Service.PrenotazioneService;
import com.example.demo.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
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


    //
    @Bean
    CommandLineRunner runner(LineaService ls, UserService us, PrenotazioneService ps, FermataRepository fs) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ClassLoader cl = this.getClass().getClassLoader();
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
                Resource[] resources = resolver.getResources("classpath*:/json/*.json");
                for (Resource r : resources) {
                    LineaDTO linea = mapper.readValue(ResourceUtils.getFile("classpath:json/" + r.getFilename()), LineaDTO.class);
                    ls.save(linea);
                }
                ListaUtentiDTO utenti = mapper.readValue(ResourceUtils.getFile("classpath:json_new/utenti.json"), ListaUtentiDTO.class);
                for(UtenteDTO u : utenti.getUtenti())
                    us.save(u.convertToEntity());
                PrenotatoDTO prenotazione = mapper.readValue(ResourceUtils.getFile("classpath:json_new/prenotazioni.json"), PrenotatoDTO.class);
                ps.save(prenotazione.convertToEntity(fs));
            } catch (Exception e) {
                System.out.println("Impossibile salvare la linea: ");
                e.printStackTrace();
            }
        };
    }

}
