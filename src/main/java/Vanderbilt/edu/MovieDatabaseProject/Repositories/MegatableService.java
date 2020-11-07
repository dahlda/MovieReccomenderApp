package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import Vanderbilt.edu.MovieDatabaseProject.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MegatableService implements MegatableRepositoryInterface {

    @Autowired
    private megatable1Repository repoA;

    @Autowired
    private megatable2Repository repoB;

    @Override
    public List<megatable1> findAllOne() {
        return (List<megatable1>) repoA.findAll();
    }

    @Override
    public List<megatable2> findAllTwo() {
        return (List<megatable2>) repoB.findAll();
    }
}
