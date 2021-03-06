package com.example.demo;

import com.example.demo.DTO.*;
import com.example.demo.Repository.FermataRepository;
import com.example.demo.Repository.LineaRepository;
import com.example.demo.Service.CorsaService;
import com.example.demo.Service.LineaService;
import com.example.demo.Service.PrenotazioneService;
import com.example.demo.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.persistence.EntityManagerFactory;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
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


    //
    @Bean
    CommandLineRunner runner(CorsaService cs, LineaService ls, UserService us, PrenotazioneService ps, LineaRepository lr, FermataRepository fs) {
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
                }
                ListaUtentiDTO utenti = mapper.readValue(new ClassPathResource("json_new/utenti.json").getInputStream(), ListaUtentiDTO.class);
                for(UtenteDTO u : utenti.getUtenti())
                    us.save(u.convertToEntity());
                ListaPrenotatiDTO prenotazioni = mapper.readValue(new ClassPathResource("json_new/prenotazioni.json").getInputStream(), ListaPrenotatiDTO.class);
                for(PrenotatoDTO p:prenotazioni.getPrenotazioni())
                  ps.save(p.convertToEntity(fs));
                ListaCorseDTO corse = mapper.readValue(new ClassPathResource("json_new/corse.json").getInputStream(), ListaCorseDTO.class);
                for(CorsaDTO c:corse.getCorse())
                    cs.save(c.convertToEntity(lr));
            } catch (Exception e) {
                System.out.println("Impossibile salvare la linea: ");
                e.printStackTrace();
            }
        };
    }

}
