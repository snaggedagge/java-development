package dkarlsso.portal.domain.model.credentials;



import dkarlsso.authentication.AuthorityType;
import lombok.*;
import portalconnector.model.Permission;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7247442082182524651L;

    @EmbeddedId
    private UniqueAuthId authId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String profilePictureLink;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String authorities;

    public boolean isLoggedIn() {
        return email != null && authId != null;
    }

    public Permission getPermission() {
        if (authorities == null) {
            return Permission.UNAUTHORIZED;
        }
        else if(authorities.contains(AuthorityType.CAN_EXECUTE_AS_ROOT.name())) {
            return Permission.SUPERUSER;
        }
        else if(authorities.contains(AuthorityType.WRITE_PRIVILEGE.name())) {
            return Permission.ADMIN;
        }
        else if(authorities.contains(AuthorityType.READ_AUTHORITY.name())) {
            return Permission.AUTHORIZED;
        }
        return Permission.UNAUTHORIZED;
    }
}
