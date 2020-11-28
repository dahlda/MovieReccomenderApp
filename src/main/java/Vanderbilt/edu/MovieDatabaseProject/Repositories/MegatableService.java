package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Service
public class MegatableService implements MegatableRepositoryInterface {

    @Autowired
    private megatable1Repository repoA;

    @Autowired
    private megatable2Repository repoB;

    @Autowired
    EntityManager em;

    @Override
    public List<megatable1> findAllOne() {
        return (List<megatable1>) repoA.findAll();
    }

    @Override
    public List<megatable2> findAllTwo() {
        return (List<megatable2>) repoB.findAll();
    }

    public List<Integer> reccomendMovies(double a, double b, double c, double d, double e){

        Query q = em.createNativeQuery("{CALL RECCOMEND_MOVIES_BY_PERSONALITY(?, ?, ?, ?, ?)}")
                .setParameter(1, a).setParameter(2, b).setParameter(3, c).setParameter(4, d)
                .setParameter(5, e);
        List<Integer> results = q.getResultList();
        return results;
    }

    public List<Integer> topMovies(){
        Query q = em.createNativeQuery("CALL TOP_MOVIES()");

        List<Integer> results = q.getResultList();
        return results;
    }

    public int getRandomMovie(){
        Query q = em.createNativeQuery("select movie_id from megatable2 order by RAND() limit 1");
        return q.getFirstResult();
    }

    public void addRec(long movieID, double rating) throws SQLException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:8889/personalitydb", "root", "root");

        Statement stmt = connection.createStatement();
        stmt.execute("CALL INSERT_RATING(" + movieID + "," + rating + ",\"" + dt + "\");");
    }

    public List<megatable2> returnRatings(){
        Query q = em.createNativeQuery("select movie_id, rating, date_time from megatable2\n" +
                "ORDER BY tstamp desc\n" +
                "limit 10;");

        List<Object> results = q.getResultList();
        ArrayList<megatable2> temp = new ArrayList<>();
        Iterator itr = results.iterator();
        while(itr.hasNext()){
            megatable2 t = new megatable2();
            Object[] obj = (Object[]) itr.next();
            t.setMovie_id((int) obj[0]);
            t.setRating((BigDecimal) obj[1]);
            t.setDATETIME((String) obj[2]);
            temp.add(t);
        }

        return temp;
    }

}
