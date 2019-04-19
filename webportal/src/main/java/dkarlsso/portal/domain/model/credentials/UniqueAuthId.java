package dkarlsso.portal.domain.model.credentials;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class UniqueAuthId implements Serializable {

    private static final long serialVersionUID = 6045067259833704408L;

    @Column(name = "authService", nullable = false)
    /** AKA registrationId */
    private String authService;

    @Column(name = "principalName", nullable = false)
    /** This is an ID from oauth2 service*/
    private String principalName;
}
