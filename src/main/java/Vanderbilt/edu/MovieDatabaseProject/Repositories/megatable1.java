package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import org.springframework.data.jpa.repository.query.Procedure;

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

    private double openness;
    private double agreeableness;
    private double emotional_stability;

    private double conscientiousness, extraversion, assigned_metric, assigned_condition;
    private int movie_1;
    private int movie_2;
    private int movie_3;
    private int movie_4;
    private int movie_5;
    private int movie_6;
    private int movie_7;
    private int movie_8;
    private int predicted_rating_8;
    private int movie_9;
    private int movie_10;
    private int movie_11;
    private int movie_12;
    private int is_personalized;
    private int enjoy_watching;
    private double predicted_rating_1, predicted_rating_2, predicted_rating_3, predicted_rating_4, predicted_rating_5, predicted_rating_6, predicted_rating_7, predicted_rating_9, predicted_rating_10, predicted_rating_11, predicted_rating_12;

    public megatable1(){}

    public megatable1(String userID, double openness, double agreeableness, double emotional_stability){

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

    public double getOpenness() {
        return openness;
    }

    public void setOpenness(double openness) {
        this.openness = openness;
    }

    public double getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(double agreeableness) {
        this.agreeableness = agreeableness;
    }

    public double getEmotional_stability() {
        return emotional_stability;
    }

    public void setEmotional_stability(double emotional_stability) {
        this.emotional_stability = emotional_stability;
    }


    public double getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public double getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(double extraversion) {
        this.extraversion = extraversion;
    }

    public double getAssigned_metric() {
        return assigned_metric;
    }

    public void setAssigned_metric(double assigned_metric) {
        this.assigned_metric = assigned_metric;
    }

    public double getAssigned_condition() {
        return assigned_condition;
    }

    public void setAssigned_condition(double assigned_condition) {
        this.assigned_condition = assigned_condition;
    }

    public int getMovie_1() {
        return movie_1;
    }

    public void setMovie_1(int movie_1) {
        this.movie_1 = movie_1;
    }

    public int getMovie_2() {
        return movie_2;
    }

    public void setMovie_2(int movie_2) {
        this.movie_2 = movie_2;
    }

    public int getMovie_3() {
        return movie_3;
    }

    public void setMovie_3(int movie_3) {
        this.movie_3 = movie_3;
    }

    public int getMovie_4() {
        return movie_4;
    }

    public void setMovie_4(int movie_4) {
        this.movie_4 = movie_4;
    }

    public int getMovie_5() {
        return movie_5;
    }

    public void setMovie_5(int movie_5) {
        this.movie_5 = movie_5;
    }

    public int getMovie_6() {
        return movie_6;
    }

    public void setMovie_6(int movie_6) {
        this.movie_6 = movie_6;
    }

    public int getMovie_7() {
        return movie_7;
    }

    public void setMovie_7(int movie_7) {
        this.movie_7 = movie_7;
    }

    public int getMovie_8() {
        return movie_8;
    }

    public void setMovie_8(int movie_8) {
        this.movie_8 = movie_8;
    }

    public int getPredicted_rating_8() {
        return predicted_rating_8;
    }

    public void setPredicted_rating_8(int predicted_rating_8) {
        this.predicted_rating_8 = predicted_rating_8;
    }

    public int getMovie_9() {
        return movie_9;
    }

    public void setMovie_9(int movie_9) {
        this.movie_9 = movie_9;
    }

    public int getMovie_10() {
        return movie_10;
    }

    public void setMovie_10(int movie_10) {
        this.movie_10 = movie_10;
    }

    public int getMovie_11() {
        return movie_11;
    }

    public void setMovie_11(int movie_11) {
        this.movie_11 = movie_11;
    }

    public int getMovie_12() {
        return movie_12;
    }

    public void setMovie_12(int movie_12) {
        this.movie_12 = movie_12;
    }

    public int getIs_personalized() {
        return is_personalized;
    }

    public void setIs_personalized(int is_personalized) {
        this.is_personalized = is_personalized;
    }

    public int getEnjoy_watching() {
        return enjoy_watching;
    }

    public void setEnjoy_watching(int enjoy_watching) {
        this.enjoy_watching = enjoy_watching;
    }

    public double getPredicted_rating_1() {
        return predicted_rating_1;
    }

    public void setPredicted_rating_1(double predicted_rating_1) {
        this.predicted_rating_1 = predicted_rating_1;
    }

    public double getPredicted_rating_2() {
        return predicted_rating_2;
    }

    public void setPredicted_rating_2(double predicted_rating_2) {
        this.predicted_rating_2 = predicted_rating_2;
    }

    public double getPredicted_rating_3() {
        return predicted_rating_3;
    }

    public void setPredicted_rating_3(double predicted_rating_3) {
        this.predicted_rating_3 = predicted_rating_3;
    }

    public double getPredicted_rating_4() {
        return predicted_rating_4;
    }

    public void setPredicted_rating_4(double predicted_rating_4) {
        this.predicted_rating_4 = predicted_rating_4;
    }

    public double getPredicted_rating_5() {
        return predicted_rating_5;
    }

    public void setPredicted_rating_5(double predicted_rating_5) {
        this.predicted_rating_5 = predicted_rating_5;
    }

    public double getPredicted_rating_6() {
        return predicted_rating_6;
    }

    public void setPredicted_rating_6(double predicted_rating_6) {
        this.predicted_rating_6 = predicted_rating_6;
    }

    public double getPredicted_rating_7() {
        return predicted_rating_7;
    }

    public void setPredicted_rating_7(double predicted_rating_7) {
        this.predicted_rating_7 = predicted_rating_7;
    }

    public double getPredicted_rating_9() {
        return predicted_rating_9;
    }

    public void setPredicted_rating_9(double predicted_rating_9) {
        this.predicted_rating_9 = predicted_rating_9;
    }

    public double getPredicted_rating_10() {
        return predicted_rating_10;
    }

    public void setPredicted_rating_10(double predicted_rating_10) {
        this.predicted_rating_10 = predicted_rating_10;
    }

    public double getPredicted_rating_11() {
        return predicted_rating_11;
    }

    public void setPredicted_rating_11(double predicted_rating_11) {
        this.predicted_rating_11 = predicted_rating_11;
    }

    public double getPredicted_rating_12() {
        return predicted_rating_12;
    }

    public void setPredicted_rating_12(double predicted_rating_12) {
        this.predicted_rating_12 = predicted_rating_12;
    }


}
