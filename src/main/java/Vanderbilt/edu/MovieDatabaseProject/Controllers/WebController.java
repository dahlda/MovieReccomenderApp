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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String landing(){ return "<h1>Welcome to the Movie Reccomender Database!</h1><p>" +
            "Welcome! This is a Java Spring and MySQL powered movie reccomendation web app. " +
            "Here, you can recieve reccomendations from movies all around the world and all throughout time. These " +
            "reccomendations are based on a quick survey that will quantify the prevalence of 5 of your " +
            "personality traits. You" +
            " can also make reccomendations of your own and contribute to the project, if you so choose! " +
            "There are 3 links accessible below: the first will take you to a movie reccomendation" +
            " form, where you can add your input into the database. The second takes you to the Movie Database, " +
            "the website we make API calls to in order to get extra info about all our movies. You can use the second" +
            " link to find the necessary ID information you'll need to make movie recs. The last link will " +
            "take you to a form that will reccomend movies to you based on self-reported personality characteristics!" +
            " These reccomendations are made based on thousands of other users who have already rated movies and " +
            "provided personality input. We hope you'll enjoy, and that you find some interesting new movies!" +
            "<h1><input type=\"button\" onclick=\"location.href='http://localhost:8080/makeRec';\" value=\"Make a Movie Reccomendation (Check out the movie IDs first)\" /></h1>" +
            "<h1><input type=\"button\" onclick=\"location.href='https://www.themoviedb.org';\" value=\"Search for Movie IDs\" /></h1>" +
            "<h1><input type=\"button\" onclick=\"location.href='http://localhost:8080/userEntry';\" value=\"Get Personalized Movie Recs\" /></h1>";}


    @GetMapping("/dbConnection")
    public String showUserInfo(){

        List<megatable1> temp;
        temp = repoService.findAllOne();
        megatable1 t = temp.get(0);
        String returnVal = "user " + t.getUserID() + " has an agreeableness score of: " + t.getAgreeableness();
        return returnVal;
    }

    @GetMapping("/makeRec")
    public String makeRec(){
        return "<t> Please submit a rating for your movie of choice, " +
                "then add your personality profile" +
                "<form action=newReccomendation method=\"get\">\n" +
                "  <label for=\"movieID\">Movie ID:</label><br>\n" +
                "  <input type=\"text\" id=\"movieID\" name=\"movieID\" value=\"\"><br>\n" +
                "  <label for=\"rating\">Rating (1.0-5.0):</label><br>\n" +
                "  <input type=\"text\" id=\"rating\" name=\"rating\" value=\"\"><br><br>\n" +
                "  <label for=\"openness\">Openness:</label><br>\n" +
                "  <input type=\"text\" id=\"openness\" name=\"openness\" value=\"\"><br>\n" +
                "  <label for=\"agreeableness\">Agreeableness:</label><br>\n" +
                "  <input type=\"text\" id=\"agreeableness\" name=\"agreeableness\" value=\"\"><br>\n" +
                "  <label for=\"emotional_stability\">Emotional Stability:</label><br>\n" +
                "  <input type=\"text\" id=\"emotional_stability\" name=\"emotional_stability\" value=\"\"><br>\n" +
                "  <label for=\"conscientiousness\">Conscientiousness:</label><br>\n" +
                "  <input type=\"text\" id=\"conscientiousness\" name=\"conscientiousness\" value=\"\"><br>\n" +
                "  <label for=\"extraversion\">Extraversion:</label><br>\n" +
                "  <input type=\"text\" id=\"extraversion\" name=\"extraversion\" value=\"\"><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form>";
    }

    @GetMapping("/newReccomendation")
    public String recValidation(int movieID, double rating, double openness, double agreeableness,
                                double emotional_stability, double conscientiousness, double extraversion)
            throws SQLException {

        megatable1 m = new megatable1();
        m.setAgreeableness(agreeableness);
        m.setConscientiousness(conscientiousness);
        m.setEmotional_stability(emotional_stability);
        m.setExtraversion(extraversion);
        m.setOpenness(openness);

        megatable1 x = repoService.repoA.save(m);


        repoService.addRec(x.getUserID(), movieID, rating);
        List<megatable2> recs = repoService.returnRatings();
        String results = "<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"https://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Submit User Personality Info</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "</head>\n" +
                "<body>";

        for(megatable2 n: recs){
            results += n.toString();
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
