package dkarlsso.portal.infrastructure.config;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import dkarlsso.portal.domain.model.UserRepository;
import dkarlsso.portal.domain.model.credentials.NoSuchUserException;
import dkarlsso.portal.domain.model.credentials.UniqueAuthId;
import dkarlsso.portal.domain.model.credentials.User;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class DefaultOauth2ClientService implements OAuth2AuthorizedClientService {

    @Autowired
    private UserRepository userRepository;

    // TODO: BEGONE DEMON
    final Map<UniqueAuthId, User> map = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {

        final UniqueAuthId authId = new UniqueAuthId(clientRegistrationId, principalName);


        if(!map.containsKey(authId)) {
            final UserInfo userInfo = userRepository.findByAuthId(new UniqueAuthId(clientRegistrationId, principalName))
                    .orElseThrow(() -> new NoSuchUserException(
                            "User not found with regId: " + clientRegistrationId + " And principal " + principalName));

            return (T) new User(userInfo, null, null, null, null);

        }

        return (T) map.get(authId);
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {

        if (principal.getPrincipal() instanceof DefaultOidcUser) {
            final DefaultOidcUser defaultOidcUser = (DefaultOidcUser) principal.getPrincipal();

            final String firstName = defaultOidcUser.getGivenName();
            final String lastName = defaultOidcUser.getFamilyName();
            final String email = defaultOidcUser.getEmail();
            final String pictureLink = defaultOidcUser.getPicture();

            // AKA registrationId
            final String authService = authorizedClient.getClientRegistration().getRegistrationId();
            final String principalName = authorizedClient.getPrincipalName();

            final Optional<UserInfo> userInfoOptional = userRepository.findByEmail(email);
            final String authorities;
            if (userInfoOptional.isPresent()) {
                authorities = userInfoOptional.get().getAuthorities();
            }
            else {
                authorities = getAuthorities(principal);
            }

            final UniqueAuthId authId = new UniqueAuthId(authService, principalName);

            final UserInfo userInfo = UserInfo.builder()
                    .authorities(authorities)
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .authId(authId)
                    .profilePictureLink(pictureLink).build();

            final User user = new User(userInfo, authorizedClient.getClientRegistration(),
                    principalName, authorizedClient.getAccessToken(), authorizedClient.getRefreshToken());
            userRepository.save(userInfo);
            map.put(authId, user);
            return;
        }
        throw new NoSuchUserException("Principal not of correct type" + principal.getPrincipal().getClass().getSimpleName());
    }

    private String getAuthorities(final Authentication principal) {
        String authorities = "";
        for(Object authority : principal.getAuthorities()) {
            if (authority instanceof OidcUserAuthority) {
                final String auth = ((OidcUserAuthority) authority).getAuthority();
                if(StringUtils.isNotBlank(authorities)) {
                    authorities += "|";
                }
                authorities += auth;
            }
            else {
                throw new NoSuchUserException("Authority not of correct type" + authority.getClass().getSimpleName());
            }
        }
        return authorities;
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        System.out.println("Was removed");
        userRepository.deleteByAuthId(new UniqueAuthId(clientRegistrationId, principalName));
        final UniqueAuthId authId = new UniqueAuthId(clientRegistrationId, principalName);
        map.remove(authId);

    }
}
