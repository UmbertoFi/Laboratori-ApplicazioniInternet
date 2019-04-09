package it.polito.ai.lab2.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ai.lab2.demo.Entity.Linea;
import it.polito.ai.lab2.demo.Repository.FermataRepository;
import it.polito.ai.lab2.demo.Repository.LineaRepository;
import it.polito.ai.lab2.demo.Service.FermataService;
import it.polito.ai.lab2.demo.Service.LineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.File;

//@JsonIgnoreProperties(ignoreUnknown = true)
@SpringBootApplication
public class DemoApplication {

    @Autowired
    LineaService serv;
    @Autowired
    FermataService servF;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(FermataRepository fermataRepository, LineaRepository lineaRepository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
             //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                ClassLoader cl = this.getClass().getClassLoader();
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
                Resource[] resources = resolver.getResources("classpath*:/json/*.json");
                for(Resource r : resources) {
                    Linea linea = mapper.readValue(ResourceUtils.getFile("classpath:json/"+r.getFilename()), Linea.class);
                    serv.addLinea(linea);
                    servF.addFermate(linea);
                    }
            } catch (Exception e) {
                System.out.println("Impossibile salvare la linea: " );
                e.printStackTrace();
            }
        };
    }
}
