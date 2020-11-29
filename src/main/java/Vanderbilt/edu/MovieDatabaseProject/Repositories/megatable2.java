package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "megatable2")
public class megatable2 {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String user_id;

    private int movie_id;
    private BigDecimal rating;
    private String dateTime;

    public String toString(){
        return "<h1> user_id: " + user_id +  ", movie_id: " + movie_id + ", rating: " + rating + ", date of rating: "
                + dateTime + "</h1>";
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    private Timestamp date;

    public megatable2(){}

    public megatable2(String userID, int movieID, BigDecimal rating, String dt){

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

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDATETIME(String dateTime) {
        this.dateTime = dateTime;
    }
}
