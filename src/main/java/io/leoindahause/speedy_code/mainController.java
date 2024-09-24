package io.leoindahause.speedy_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.leoindahause.model.Exercise;
import io.leoindahause.model.User;
import io.leoindahause.repository.ExerciseRepository;
import io.leoindahause.repository.UserRepository;
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
    public String register(HttpSession session, Model model) {

        if (session.getAttribute("user") != null) {
            model.addAttribute("userEmail", ((User) session.getAttribute("user")).getEmail());
            return "user";
        }

        return "register";
    }

    @PostMapping("/register_user")
    @Transactional
    public String registerUser(@RequestParam String email, @RequestParam String password,
            @RequestParam String conf_password, Model model, HttpSession session) {
        if (!password.equals(conf_password)) {
            model.addAttribute("errorMessage", "The passwords do not match.");
            return "register";
        }

        if (session.getAttribute("user") != null) {
            return "user";
        }

        boolean emailExists = userRepository.existsByEmail(email);

        if (emailExists) {
            model.addAttribute("errorMessage", "The email already exists.");
            return "register";

        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            user = userRepository.save(user);

            Exercise exercise = new Exercise();
            exercise.setUser(user);

            exerciseRepository.save(exercise);

            session.setAttribute("user", user);
            model.addAttribute("userEmail", user.getEmail());

            return "user";
        }
    }

    @PostMapping({ "/login_user" })
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("errorMessage2", "Incorrect email or password.");
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

    @PostMapping("/change_email")
    @Transactional
    public String changeEmail(@RequestParam String email, @RequestParam String conf_email, HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");

        if (!email.equals(conf_email)) {
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("errorMessage1", "The emails do not match.");
            return "user";
        }

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("errorMessage1", "The email already exists.");
            return "user";
        }

        try {
            user.setEmail(email);
            userRepository.save(user);

            session.setAttribute("user", user);
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("infoMessage1", "Email updated successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage1",
                    "Error updating the email. Please try again.");
            return "user";
        }

        return "user";
    }

    @PostMapping("/change_password")
    @Transactional
    public String changePassword(@RequestParam String current_password, @RequestParam String new_password,
            @RequestParam String conf_password, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (!user.getPassword().equals(current_password)) {
            model.addAttribute("errorMessage2", "Incorrect current password.");
            return "user";
        }

        if (!new_password.equals(conf_password)) {
            model.addAttribute("errorMessage2", "The passwords do not match.");
            return "user";
        }

        user.setPassword(conf_password);
        userRepository.save(user);
        model.addAttribute("infoMessage2", "Password updated successfully.");

        return "user";
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
        Exercise exercise = null;

        if (user != null) {
            model.addAttribute("userEmail", user.getEmail());
            exercise = exerciseRepository.findByUserId(user.getId());
        }

        if (exercise == null) {
            exercise = new Exercise();
        }

        model.addAttribute("color1", getColor(exercise.isExercise1()));
        model.addAttribute("color2", getColor(exercise.isExercise2()));
        model.addAttribute("color3", getColor(exercise.isExercise3()));
        model.addAttribute("color4", getColor(exercise.isExercise4()));
        model.addAttribute("color5", getColor(exercise.isExercise5()));
        model.addAttribute("color6", getColor(exercise.isExercise6()));
        model.addAttribute("color7", getColor(exercise.isExercise7()));
        model.addAttribute("color8", getColor(exercise.isExercise8()));
        model.addAttribute("color9", getColor(exercise.isExercise9()));
        model.addAttribute("color10", getColor(exercise.isExercise10()));
        model.addAttribute("color11", getColor(exercise.isExercise11()));
        model.addAttribute("color12", getColor(exercise.isExercise12()));

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

    @GetMapping("/easy")
    public String easy() {
        return "easy";
    }

    @GetMapping("/intermedium")
    public String intermedium() {
        return "intermedium";
    }

    @GetMapping("/hard")
    public String hard() {
        return "hard";
    }

    @GetMapping("/h1")
    public String h1() {
        return "h1";
    }

    @GetMapping("/h21")
    public String h21() {
        return "h21";
    }

    @GetMapping("/h3")
    public String h3() {
        return "h3";
    }

    @GetMapping("/h4")
    public String h4() {
        return "h4";
    }

    @GetMapping("/i1")
    public String i1() {
        return "i1";
    }

    @GetMapping("/i2")
    public String i2() {
        return "i2";
    }

    @GetMapping("/i3")
    public String i3() {
        return "i3";
    }

    @GetMapping("/i4")
    public String i4() {
        return "i4";
    }

    @GetMapping("/e1")
    public String e1() {
        return "e1";
    }

    @GetMapping("/e2")
    public String e2() {
        return "e2";
    }

    @GetMapping("/e3")
    public String e3() {
        return "e3";
    }

    @GetMapping("/e4")
    public String e4() {
        return "e4";
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

    public String getColor(Boolean value) {
        if (value) {
            return "completed";
        } else {
            return "not_completed";
        }
    }
}