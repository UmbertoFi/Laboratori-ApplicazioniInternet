package it.polito.ai.lab1.demo;

import it.polito.ai.lab1.demo.ViewModels.RegistrationVM;
import it.polito.ai.lab1.demo.ViewModels.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    @Bean
    Map<String, UserRecord> mapGenerator(){
        return new HashMap<String,UserRecord>();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
