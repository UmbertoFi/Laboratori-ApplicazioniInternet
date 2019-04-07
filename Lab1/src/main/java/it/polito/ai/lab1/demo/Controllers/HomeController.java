package it.polito.ai.lab1.demo.Controllers;

import it.polito.ai.lab1.demo.ViewModels.LoginVM;
import it.polito.ai.lab1.demo.ViewModels.RegistrationVM;
import it.polito.ai.lab1.demo.ViewModels.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private Map<String, UserRecord> utenti;

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/register")
    public String register() { return "register"; }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/privata")
    public String privata() {
        return "privata";
    }

    @PostMapping("/register")
    public String validateRegistration(@Valid RegistrationVM vm, BindingResult res, Model m) {
        if (res.hasErrors()) {
            if (res.getFieldErrors("nome").isEmpty() == false)
                m.addAttribute("msgNome", "Nome non valido!");
            if (res.getFieldErrors("cognome").isEmpty() == false)
                m.addAttribute("msgCognome", "Cognome non valido!");
            if (res.getFieldErrors("mail").isEmpty() == false)
                m.addAttribute("msgEmail", "E-mail non valida!");
            if (res.getFieldErrors("pass1").isEmpty() == false)
                m.addAttribute("msgPass1", "Password non valida!");
            if (res.getFieldErrors("pass2").isEmpty() == false)
                m.addAttribute("msgPass2", "Password non valida!");
            if (res.getFieldErrors("privacy").isEmpty() == false)
                m.addAttribute("msgPrivacy", "Privacy non accettata!");
            rewriteRegistrationFields(vm, m);
            return "register";
        } else {
            if (vm.getPass1().compareTo(vm.getPass2()) != 0) {
                m.addAttribute("msgPass2", "La password non coincide con la precedente!");
                rewriteRegistrationFields(vm, m);
                return "register";
            }
            if (vm.isPrivacy() == false) {
                m.addAttribute("msgPrivacy", "Privacy non accettata!");
                rewriteRegistrationFields(vm, m);
                return "register";
            }
            if(utenti.containsKey(vm.getMail())){
                m.addAttribute("msgEmail", "Utente già registrato!");
                rewriteRegistrationFields(vm, m);
                return "register";
            }
            m.addAttribute("messageLogin", "Registrazione avvenuta con successo!");
            utenti.put(vm.getMail(), new UserRecord(vm.getNome(), vm.getCognome(), vm.getPass1()));
            return "redirect:login";
        }
    }

    @PostMapping("/login")
    public String login(@Valid LoginVM vm, BindingResult res, Model m) {
        if (res.hasErrors()) {
            if (res.getFieldErrors("mail").isEmpty() == false)
                m.addAttribute("msgEmailLogin", "E-mail non valida!");
            if (res.getFieldErrors("pass1").isEmpty() == false)
                m.addAttribute("msgPass1Login", "Password non valida!");
            rewriteLoginFields(vm, m);
            return "login";
        } else {
            if (utenti.containsKey(vm.getMail())) {
                if (utenti.get(vm.getMail()).getPass().compareTo(vm.getPass1()) == 0) {
                    m.addAttribute("messagePrivata", "Login effettuato, benvenuto utente " + vm.getMail());
                    return "redirect:privata";
                } else {
                    m.addAttribute("msgPass1Login", "Password errata!");
                    rewriteLoginFields(vm, m);
                    return "login";
                }
            } else {
                m.addAttribute("msgEmailLogin", "Utente non esistente!");
                rewriteLoginFields(vm, m);
                return "login";
            }
        }
    }

    private void rewriteRegistrationFields(RegistrationVM vm, Model m){
        m.addAttribute("nome", vm.getNome());
        m.addAttribute("cognome", vm.getCognome());
        m.addAttribute("mail", vm.getMail());
        m.addAttribute("pass1", vm.getPass1());
        m.addAttribute("pass2", vm.getPass2());
    }

    private void rewriteLoginFields(LoginVM vm, Model m){
        m.addAttribute("mailLogin", vm.getMail());
        m.addAttribute("passLogin", vm.getPass1());
    }
}
