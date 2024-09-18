package io.leoindahause.speedy_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.leoindahause.model.User;
import io.leoindahause.repository.UserRepository;
import io.leoindahause.model.Exercise;
import io.leoindahause.repository.ExerciseRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class mainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping({ "/", "/index", "/index/" })
    public String root() {
        return "index";
    }

    @GetMapping({ "/register", "/register/" })
    public String register() {
        return "register";
    }

    @PostMapping("/register_user")
    @Transactional
    public String registerUser(@RequestParam String email, @RequestParam String password, @RequestParam String conf_password, Model model, HttpSession session) {
        if (!password.equals(conf_password)) {
            model.addAttribute("errorMessage", "Las contraseñas no coinciden.");
            return "register";
        }
        
        if (session.getAttribute("user") != null) {
            return "user";
        }

        boolean emailExists = userRepository.existsByEmail(email);

        if (emailExists) {
            model.addAttribute("errorMessage", "El correo electrónico ya existe.");
            return "register"; 
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
        
            user = userRepository.save(user); // Ensure user is fully persisted and get the updated user object
            
            Exercise exercise = new Exercise();
            exercise.setUser(user);

            exerciseRepository.save(exercise);
            
            session.setAttribute("user", user);
            model.addAttribute("userEmail", user.getEmail());
        
            return "user";
        }
    }

    @PostMapping({"/login_user", "/login"})
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("errorMessage2", "Correo electrónico o contraseña incorrectos.");
            return "register";
        }

        session.setAttribute("user", user);
        model.addAttribute("userEmail", user.getEmail());

        return "user";
    }

    @GetMapping({ "/user", "/user/", "/register_user" })
    public String user(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            model.addAttribute("userEmail", user.getEmail());
            return "user";
        }

        return "register";
    }

    @GetMapping({ "/difficulty", "/difficulty/" })
    public String difficulty(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("userEmail", user.getEmail());
        }

        return "difficulty";
    }

    @GetMapping({ "/list", "/list/" })
    public String list(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("userEmail", user.getEmail());
        }

        return "list";
    }

    @GetMapping({ "/error" })
    public String error() {
        return "error";
    }

    @GetMapping("/code")
    public String code() {
        return "code";
    }

    @GetMapping("/log_out")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "index";
    }
    
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}