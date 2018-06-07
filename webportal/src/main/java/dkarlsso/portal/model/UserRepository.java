package dkarlsso.portal.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByFacebookId(String facebookId);

    User findByFacebookId(String facebookId);

}
