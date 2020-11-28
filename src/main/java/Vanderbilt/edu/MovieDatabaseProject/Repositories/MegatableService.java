package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.CallableStatement;
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
}
