package com.example.demo;

import com.example.demo.DTO.LineaDTO;
import com.example.demo.Service.LineaService;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //
    @Bean
    CommandLineRunner runner(LineaService ls) {
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
            } catch (Exception e) {
                System.out.println("Impossibile salvare la linea: ");
                e.printStackTrace();
            }
        };
    }

}
