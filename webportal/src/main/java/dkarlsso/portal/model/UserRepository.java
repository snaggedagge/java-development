package dkarlsso.portal.model;

import dkarlsso.portal.model.credentials.UniqueAuthId;
import dkarlsso.portal.model.credentials.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {

    boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findByAuthId(UniqueAuthId authId);

    void deleteByAuthId(UniqueAuthId authId);

}
