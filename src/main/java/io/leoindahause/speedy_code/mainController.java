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
    private Person user;

    @GetMapping({"/", "/index", "/index/"})
    public String root() {
        if (user == null) {
            user = setGuest();
        }
        return "index";
    }
    
    @GetMapping({"/register", "/register/"})
    public String register() {
        return "register";
    }

    @PostMapping({"/register_user", "/register_user/"})
    public String registerPost(@ModelAttribute Person person, org.springframework.ui.Model model) {
        System.out.println(person.toString());
    
        Person person_in = person_rep.save(person);
        model.addAttribute("email", user.getEmail()); 
        
        user = person_in;   
        return "successful"; 
    }

    @GetMapping({"/difficulty", "/difficulty/"})
    public String difficulty() {
        return "difficulty";
    } 

    @GetMapping({"/list", "/list/"})
    public String list() {
        return "list";
    }
    
    @GetMapping({"/user", "/user/"})
    public String user() {
        return "user";
    }
    
    @GetMapping({"/exercise", "/exercise/"})
    public String exersise() {
        return "exercise";
    }
    
    public Person setGuest() {
        Person guestUser = person_rep.findById(0).orElse(null);

        if (guestUser != null) {
            System.out.println(guestUser);
        } else {
            System.out.println("Guest user not found.");
        }

        return guestUser;
    }
}