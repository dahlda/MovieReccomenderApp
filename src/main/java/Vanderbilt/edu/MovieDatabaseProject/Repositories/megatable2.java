package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "megatable2")
public class megatable2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String user_id;

    private Long movie_id;
    private float rating;
    private String dateTime;

    public megatable2(){}

    public megatable2(String userID, Long movieID, float rating, String dt){

        this.user_id = userID;
        this.movie_id = movieID;
        this.rating = rating;
        this.dateTime = dt;

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDATETIME(String dateTime) {
        this.dateTime = dateTime;
    }
}
