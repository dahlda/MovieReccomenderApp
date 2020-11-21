package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class userController {

    public WebController web;

    @GetMapping("/userEntry")
    public String newUserForm(Model model){
        model.addAttribute("newUser", new newUser());
        return "newUser";
    }

    @PostMapping("/userEntry")
    public String formSubmitted(@ModelAttribute newUser newUser, Model model) {
        model.addAttribute("newUser", newUser);
        return "results";
    }
}
