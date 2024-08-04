package io.leoindahause.speedy_code;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    
}