package Vanderbilt.edu.MovieDatabaseProject.Controllers;

import Vanderbilt.edu.MovieDatabaseProject.JsonReader;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.MegatableService;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.megatable1;
import Vanderbilt.edu.MovieDatabaseProject.Repositories.megatable2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class WebController {
    @Autowired
    private MegatableService repoService;

    @GetMapping(path = "/home")
    public String homePage(){
        return "Hello, World!";
    }

    @GetMapping(path = "/")
    public String landing(){ return "<h1> If you'd like to make a movie reccomendation, please enter a web address " +
            "with the format localhost:8080/newReccomendation" +
            "?movieID={insert ID here}&rating={insert rating from 1.0-5.0 here}</h1>" +
            "<p> Movie IDs can be searched <a href=\"https://www.themoviedb.org\">here</a></p>" +
            "<h2> If you'd like to see our reccomendations for you, click <a href=\"http://localhost:8080/userEntry\">here</a></h2>";}


    @GetMapping("/dbConnection")
    public String showUserInfo(){

        List<megatable1> temp;
        temp = repoService.findAllOne();
        megatable1 t = temp.get(0);
        String returnVal = "user " + t.getUserID() + " has an agreeableness score of: " + t.getAgreeableness();
        return returnVal;
    }

    @GetMapping("/newReccomendation")
    public String recValidation(long movieID, double rating) throws SQLException {
        repoService.addRec(movieID, rating);
        List<megatable2> recs = repoService.returnRatings();
        String results = "<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"https://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Submit User Personality Info</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "</head>\n" +
                "<body>";

        for(megatable2 m: recs){
            results += m.toString();
        }

        return results;

    }

    //double a, double b, double c, double d, double e
    @GetMapping("/topReccomendations")
    public String topReccomendations(newUser user) throws IOException, JSONException {

        double open = user.getOpenness();
        double agree = user.getAgreeableness();
        double es = user.getEmotional_stability();
        double c = user.getConscientiousness();
        double ex = user.getExtraversion();

        List<Integer> test = repoService.reccomendMovies(open, agree, es, c, ex);
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
                    || getAdult(Integer.toString(movieIDs[n]))
            || movieTitles[n].equalsIgnoreCase("Damn Yankees: Live In Japan")
            || movieTitles[n].equalsIgnoreCase("Wojtek: The Bear That Went to War")
            || getPoster(Integer.toString(movieIDs[n])).equalsIgnoreCase("Movie unavailable in Database")){
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
        if(json != null && !(json.get("poster_path").toString().equalsIgnoreCase("null"))) {
            return "https://image.tmdb.org/t/p/original" + json.get("poster_path").toString();
        }
        else{
            return "Movie unavailable in Database";
        }

    }

    public boolean getAdult(String id) throws IOException, JSONException {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=3fd6464010a6ccad6cba672c8d42cb74&language=en-US";
        JsonReader reader = new JsonReader();
        JSONObject json = reader.readJsonFromUrl(url);
        if(json != null) {
            return (boolean) json.get("adult");
        }
        else{
            return true;
        }

    }











}
