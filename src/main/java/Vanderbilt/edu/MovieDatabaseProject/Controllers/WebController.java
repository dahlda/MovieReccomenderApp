package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import Vanderbilt.edu.MovieDatabaseProject.Repositories.MegatableService;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.megatable1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebController {
    @Autowired
    private MegatableService repoService;

    @GetMapping(path = "/home")
    public String homePage(){
        return "Hello, World!";
    }

    @GetMapping("/dbConnection")
    public String showUserInfo(){

        List<megatable1> temp;
        temp = repoService.findAllOne();
        megatable1 t = temp.get(0);
        String returnVal = "user " + t.getUserID() + " has an agreeableness score of: " + t.getAgreeableness();
        return returnVal;
    }

    //double a, double b, double c, double d, double e
    @GetMapping("/topReccomendations")
    public String topReccomendations(){
        List<Integer> test = repoService.reccomendMovies(4.0, 3.0, 4.5, 2.0, 2.5);
        String results = "My top ten reccomended movies for you are: \n";
        boolean empty = false;
        int i = 0;
        do{
            if(test.get(i).equals(null) || i == 9){
                empty = true;
            }
            results = results + test.get(i).toString() + "\n";
            ++i;
        }while(!empty);

        if(i<9){
            int temp = 9-i;
            List<Integer> fillers = repoService.topMovies();
            for(int k = 0; k < temp; ++k){
                results = results + test.get(i).toString() + "\n";
            }
        }

        return results;

    }








}
