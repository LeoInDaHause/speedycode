package io.leoindahause;

// import org.hibernate.mapping.Index;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class indexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
}