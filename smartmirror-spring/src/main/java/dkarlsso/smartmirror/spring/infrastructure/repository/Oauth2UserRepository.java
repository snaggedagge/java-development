package dkarlsso.smartmirror.spring.infrastructure.repository;

import dkarlsso.commons.oauth2.Oauth2Credential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Oauth2UserRepository extends CrudRepository<Oauth2Credential, String> {
//
//    boolean existsByEmail(String email);
//
//    Optional<Oauth2Credential> findByEmail(String email);
//
//    void deleteByEmail(UniqueAuthId authId);
}
