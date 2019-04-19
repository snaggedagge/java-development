package hottub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SettingsRepository extends CrudRepository<SettingsDAO, Long> {

    boolean existsById(long id);

    SettingsDAO findById(long id);

}
