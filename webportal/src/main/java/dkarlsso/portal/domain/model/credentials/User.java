package dkarlsso.portal.domain.model.credentials;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;


public class User extends OAuth2AuthorizedClient {

    private final OAuth2AccessToken accessToken;

    private final OAuth2RefreshToken refreshToken;

    private final UserInfo userInfo;

    public User(final UserInfo userInfo,
                ClientRegistration clientRegistration,
                String principalName,
                OAuth2AccessToken accessToken,
                OAuth2RefreshToken refreshToken) {
        super(clientRegistration, principalName, accessToken, refreshToken);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfo = userInfo;
    }

    //    @ManyToMany
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
//    private Collection<Role> roles;

}
