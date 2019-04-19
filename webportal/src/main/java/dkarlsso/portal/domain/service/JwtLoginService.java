package dkarlsso.portal.domain.service;

import dkarlsso.authentication.UserAuthority;
import dkarlsso.authentication.UserDetails;
import dkarlsso.authentication.jwt.JwtAuthenticationParser;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtLoginService {

    final Map<String, Instant> assignedJwts = new HashMap<>();

    public synchronized String getJwt(final String subject, final UserInfo userInfo, final List<UserAuthority> userAuthorities) {
        for(final Map.Entry<String, Instant> entry : assignedJwts.entrySet()) {
            if (Instant.now().isAfter(entry.getValue())) {
                assignedJwts.remove(entry.getKey());
            }
        }

        final UserDetails userDetails = UserDetails.builder()
                .profilePictureLink(userInfo.getProfilePictureLink())
                .email(userInfo.getEmail())
                .lastName(userInfo.getLastName())
                .firstName(userInfo.getFirstName())
                .authorities(userAuthorities).build();

        final String jwt = JwtAuthenticationParser.authenticationToJwt(userDetails,"WebPortal", subject, 3);
        assignedJwts.put(jwt, Instant.now().plusSeconds(3));
        return jwt;
    }

    public synchronized boolean isJwtValid(final String jwt) {
        return assignedJwts.containsKey(jwt) && Instant.now().isBefore(assignedJwts.get(jwt));
    }
}
