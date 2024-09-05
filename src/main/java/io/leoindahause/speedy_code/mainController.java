package io.leoindahause.speedy_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import io.leoindahause.model.Person;
import io.leoindahause.repository.Person_rep;


@Controller
public class mainController {

    @Autowired
    private Person_rep person_rep;
    private Person user;

    @GetMapping({"/", "/index", "/index/"})
    public String root() {
        return "index";
    }
    
    @GetMapping({"/register", "/register/"})
    public String register() {
        return "register";
    }

    @PostMapping({"/register_user", "/register_user/"})
    public String registerPost(@ModelAttribute Person person, Model model) {
        System.out.println(person.toString());

        // Save the person and set the user object
        Person person_in = person_rep.save(person);
        user = person_in;

        // Add the email to the model
        if (user != null) {
            model.addAttribute("email", user.getEmail());
        } else {
            model.addAttribute("email", "Guest");
        }

        return "user";
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
    public String user(Model model) {
        if (user != null) {
            model.addAttribute("email", user.getEmail());
        } else {
            model.addAttribute("email", "Guest");
        }
        return "user";
    }
}