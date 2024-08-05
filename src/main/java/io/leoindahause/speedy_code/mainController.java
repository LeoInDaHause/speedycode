package io.leoindahause.speedy_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.leoindahause.model.Person;
import io.leoindahause.repository.Person_rep;

@Controller
public class mainController {

    @Autowired
    private Person_rep person_rep;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register-user")
    public String registerPost(@ModelAttribute Person person, org.springframework.ui.Model model) {
        System.out.println(person.toString());
    
        Person person_in = person_rep.save(person);
        model.addAttribute("email", person_in.getEmail());    
        return "successful"; 
    }

    @GetMapping("/difficulty")
    public String difficulty() {
        return "difficulty";
    } 
}