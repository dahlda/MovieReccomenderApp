package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "megatable1")
public class megatable1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userID;

    private String openness;
    private String agreeableness;
    private String emotional_stability;

    public megatable1(){}

    public megatable1(String userID, String openness, String agreeableness, String emotional_stability){

        this.userID = userID;
        this.openness = openness;
        this.agreeableness = agreeableness;
        this.emotional_stability = emotional_stability;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOpenness() {
        return openness;
    }

    public void setOpenness(String openness) {
        this.openness = openness;
    }

    public String getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(String agreeableness) {
        this.agreeableness = agreeableness;
    }

    public String getEmotional_stability() {
        return emotional_stability;
    }

    public void setEmotional_stability(String emotional_stability) {
        this.emotional_stability = emotional_stability;
    }
}
