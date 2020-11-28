package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import Vanderbilt.edu.MovieDatabaseProject.JsonReader;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.MegatableService;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.megatable1;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

        List<Integer> test = repoService.reccomendMovies(4.0, 4.0, 4.0, 4.0, 4.0);
        String[] movieTitles = new String[10];
        int[] movieIDs = new int[10];
        List<Integer> fillers = repoService.topMovies();

        int temp = test.size();

        int m = 0;

        while(m < 10){

            if(m < temp){
                movieTitles[m] = getTitle(test.get(m).toString());
                movieIDs[m] = test.get(m);
            }
            else{
                movieTitles[m] = getTitle(fillers.get(m).toString());
            }
            ++m;
        }

        int top100 = 10;
        for(int n = 0; n < 10; ++n){
            while(movieTitles[n].equalsIgnoreCase("Movie unavailable in Database")
                    || getAdult(Integer.toString(movieIDs[n])) != "false"){
                movieTitles[n] = getTitle(Integer.toString(fillers.get(top100)));
                movieIDs[n] = fillers.get(top100);
                ++top100;
            }
        }

        return moviePoster(movieIDs);

    }

    @GetMapping("/APITest")
    public String moviePoster(int[] IDs) throws IOException, JSONException {

        System.out.print(getPoster(Integer.toString(788)));
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"https://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<style>\n" +
                ".content {\n" +
                "margin: 0;\n" +
                "    text-align: center;" +
                ".img {\n" +
                "  display: block;\n" +
                "  margin-left: auto;\n" +
                "  margin-right: auto;\n" +
                "  width: 40%;\n" +
                "}"+
                "}\n" +
                ".title {\n" +
                "font-size = 50px\n" +
                "}\n" +
                "</style>" +
                "<body>\n" +
                "<div class=\"content\">\n" +
                "<h1> The following movies are reccomended for your personality profile </h1>" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[0])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[0])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[1])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[1])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[2])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[2])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[3])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[3])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[4])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[4])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[5])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[5])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[6])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[6])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[7])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[7])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[8])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[8])) + "</body>" +
                "</div>" +
                "<div class=\"content\">\n" +
                "<img class=\"img\" src='"+ getPoster(Integer.toString(IDs[9])) +"' width=\"300\" height=\"500\">\n" +
                "<body>" + getTitle(Integer.toString(IDs[9])) + "</body>" +
                "</div>" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/getTitleFromID")
    public String getTitle(String id) throws IOException, JSONException {

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

    @GetMapping("/getPosterFromID")
    public String getPoster(String id) throws IOException, JSONException {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=3fd6464010a6ccad6cba672c8d42cb74&language=en-US";
        JsonReader reader = new JsonReader();
        JSONObject json = reader.readJsonFromUrl(url);
        if(json != null) {
            return "https://image.tmdb.org/t/p/original" + json.get("poster_path").toString();
        }
        else{
            return "Movie unavailable in Database";
        }

    }

    public String getAdult(String id) throws IOException, JSONException {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=3fd6464010a6ccad6cba672c8d42cb74&language=en-US";
        JsonReader reader = new JsonReader();
        JSONObject json = reader.readJsonFromUrl(url);
        if(json != null) {
            return json.get("adult").toString();
        }
        else{
            return "Movie unavailable in Database";
        }

    }










}
