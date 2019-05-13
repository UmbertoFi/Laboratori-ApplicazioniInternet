package com.example.demo.Controller;

import com.example.demo.Entity.Utente;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.ViewModel.RecoveryVM;
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

    @GetMapping("recover/{randomUUID}")
    public String register(@PathVariable("randomUUID") String randomUUID) {

        Utente u = userService.getTokenForRecovery(randomUUID);
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione=1 h=3600000 ms
            throw new NotFoundException();
        return "recover";
    }

    @PostMapping("recover/{randomUUID}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmRegister(@PathVariable("randomUUID") String randomUUID, @Valid RecoveryVM vm, Model m) {
        if(checkValidPass(vm.getPass1(),vm.getPass2())==false){
            throw new NotFoundException();
        }
        if (vm.getPass1().compareTo(vm.getPass2()) != 0) {
            m.addAttribute("msgPass2", "La password non coincide con la precedente!");
            throw new NotFoundException();
        }

        Utente u = userService.getTokenForRecovery(randomUUID);
        Date now = new Date();
        long diff = now.getTime() - u.getExpiredToken().getTime();
        if (diff > 3600000)                         // Tempo entro il quale poter confermare la registrazione
            throw new NotFoundException();
        u.setExpiredCredential(true);
        u.setPassword(passwordEncoder.encode(vm.getPass1()));
        userService.save(u);
    }

    private boolean checkValidPass(String pass1, String pass2) {
        int l1=pass1.length();
        int l2=pass2.length();

        if(l1>7 && l1<20 && l2>7 && l2<20){
            return true;
        }
        return false;
    }
}
