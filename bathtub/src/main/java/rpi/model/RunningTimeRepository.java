package rpi.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RunningTimeRepository extends CrudRepository<RunningTime, Long> {

    boolean existsById(long id);

    RunningTime findById(long id);

}
