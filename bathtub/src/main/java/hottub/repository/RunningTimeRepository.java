package hottub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RunningTimeRepository extends CrudRepository<RunningTimeDAO, Long> {

    boolean existsById(long id);

    RunningTimeDAO findById(long id);

}
