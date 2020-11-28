package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import Vanderbilt.edu.MovieDatabaseProject.JsonReader;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.MegatableService;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.megatable1;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    public String topReccomendations() throws IOException, JSONException {

        List<Integer> test = repoService.reccomendMovies(4.0, 3.0, 4.5, 2.0, 2.5);
        String results = "My top ten reccomended movies for you are: \n";
        String[] movieTitles = new String[10];
        List<Integer> fillers = repoService.topMovies();

        boolean empty = false;
        int i = 0;
        do{
            if(test.get(i).equals(null) || i == 9){
                empty = true;
            }
            movieTitles[i] = apiTest(test.get(i).toString());
            ++i;
        }while(!empty);

        if(i<9){
            int temp = 9-i;
            for(int k = temp; k < 9; ++k){
                movieTitles[k] = results + apiTest(fillers.get(k).toString());
            }
        }

        int top100 = 10;
        for(int n = 0; n < 10; ++n){
            while(movieTitles[n].equalsIgnoreCase("Movie unavailable in Database")){
                movieTitles[n] = apiTest(Integer.toString(top100));
                ++top100;
            }
        }

        int t = 0;
        for(String title: movieTitles){
            if(t != 9) {
                results += title + ",\n";
            }
            else{
                results += "and " + title;
            }
            ++t;
        }

        return results;

    }

    @GetMapping("/APITest")
    public String apiTest(String id) throws IOException, JSONException {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=3fd6464010a6ccad6cba672c8d42cb74&language=en-US";
        JsonReader reader = new JsonReader();
        JSONObject json = reader.readJsonFromUrl(url);
        if(json != null) {
            return json.get("original_title").toString();
        }
        else{
            return "Movie unavailable in Database";
        }

    }










}
