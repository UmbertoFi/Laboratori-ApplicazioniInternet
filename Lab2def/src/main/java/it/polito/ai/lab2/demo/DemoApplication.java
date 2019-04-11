package it.polito.ai.lab2.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ai.lab2.demo.DTO.LineaDTO;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Service.FermataService;
import it.polito.ai.lab2.demo.Service.LineaService;
import it.polito.ai.lab2.demo.Service.PrenotazioneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }
//
    @Bean
    CommandLineRunner runner(FermataService fs, LineaService ls) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ClassLoader cl = this.getClass().getClassLoader();
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
                Resource[] resources = resolver.getResources("classpath*:/json/*.json");
                for(Resource r : resources) {
                    LineaDTO linea = mapper.readValue(ResourceUtils.getFile("classpath:json/"+r.getFilename()), LineaDTO.class);
                    ls.save(linea);
                }
            } catch (Exception e) {
                System.out.println("Impossibile salvare la linea: ");
                e.printStackTrace();
            }
        };
    }

}
