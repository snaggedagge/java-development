package dkarlsso.portal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portalconnector.model.Permission;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteDAO {

    @Id
    private String websiteId;

    private String websiteName;

    private String websiteDescription;

    @Lob
    private String imageBase64;

    private String websiteLink;

    private String localWebsiteLink;

    private String infoLink;

    private Permission permission;

    private Date dateSinceLastConnection;

    private boolean hasLogin;
}
