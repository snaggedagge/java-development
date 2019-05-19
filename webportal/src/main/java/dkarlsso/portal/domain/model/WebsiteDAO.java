package dkarlsso.portal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portalconnector.model.Permission;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "website")
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

    @CollectionTable(name="website_user_permissions")
    @ElementCollection
    private Map<String, Permission> specialUserPermissions;
}
