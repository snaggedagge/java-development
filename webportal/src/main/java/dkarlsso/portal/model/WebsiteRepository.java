package dkarlsso.portal.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends CrudRepository<WebsiteDAO, Long> {

    WebsiteDAO findByWebsiteName(String websiteName);

    boolean existsByWebsiteName(String websiteName);

    List<WebsiteDAO> findAll();
}
