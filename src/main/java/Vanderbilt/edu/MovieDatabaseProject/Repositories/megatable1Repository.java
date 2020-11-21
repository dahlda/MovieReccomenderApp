package Vanderbilt.edu.MovieDatabaseProject.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedStoredProcedureQuery;
import java.util.List;


@Repository
public interface megatable1Repository extends CrudRepository<megatable1, Long> {

}
