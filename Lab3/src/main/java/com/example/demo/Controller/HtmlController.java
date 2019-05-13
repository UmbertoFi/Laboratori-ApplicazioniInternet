package com.example.demo.Controller;

import com.example.demo.Entity.Utente;
import com.example.demo.RecoveryVM;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(path = "demo")
public class HtmlController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }


    @GetMapping("recover/{randomUUID}")
    public String register(@PathVariable("randomUUID") String randomUUID) {

        Utente u = userService.getTokenForRecovery(randomUUID);
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione
            throw new NotFoundException();
        return "recover";
    }

    @PostMapping("recover/{randomUUID}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmRegister(@PathVariable("randomUUID") String randomUUID, @Valid RecoveryVM vm, Model m) {
        if (vm.getPass1().compareTo(vm.getPass2()) != 0) {
            m.addAttribute("msgPass2", "La password non coincide con la precedente!");
            throw new NotFoundException();
        }

        Utente u = userService.getTokenForRecovery(randomUUID);
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 30000)                         // Tempo entro il quale poter confermare la registrazione
            throw new NotFoundException();
        u.setExpiredcredential(true);
        u.setPassword(passwordEncoder.encode(vm.getPass1()));
        userService.save(u);
    }
}
