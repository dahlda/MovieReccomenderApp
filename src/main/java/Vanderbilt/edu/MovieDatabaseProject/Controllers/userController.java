package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import Vanderbilt.edu.MovieDatabaseProject.Repositories.MegatableService;
import org.json.JSONException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

@Controller
public class userController {

    public WebController web;
    public MegatableService repo;

    @GetMapping("/userEntry")
    public String newUserForm(Model model){
        model.addAttribute("newUser", new newUser());
        return "newUser";
    }

    @PostMapping("/userEntry")
    public String formSubmitted(@ModelAttribute newUser newUser, Model model) {
        model.addAttribute("newUser", newUser);
        System.out.println(newUser.getAgreeableness());
        return "result";
    }

}
