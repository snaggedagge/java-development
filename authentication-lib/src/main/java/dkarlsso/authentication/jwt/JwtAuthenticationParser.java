package dkarlsso.authentication.jwt;

import dkarlsso.authentication.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthenticationParser {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // TODO: Not so secret ey??
    private static final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private static SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
            return TextCodec.BASE64.decode(secretKey);
        }
    };

    public static CustomAuthentication authenticationFromJwt(final String jwt, final String expectedSubject, final String expectedIssuer) {
        final JwtParser jwtParser = Jwts.parser().setSigningKeyResolver(signingKeyResolver)
                .setAllowedClockSkewSeconds(5);
        if (jwtParser.isSigned(jwt)) {
            final Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);
            if (jws.getBody().getIssuer().equalsIgnoreCase(expectedIssuer) && jws.getBody().getSubject().equalsIgnoreCase(expectedSubject)) {
                final Set<UserAuthority> authorities = UserAuthority.of(jws.getBody().get(UserClaims.USER_AUTHORITIES.name(), String.class).split(","));
                final UserDetails userDetails = UserDetails.builder()
                        .authorities(new ArrayList<>(authorities))
                        .firstName(jws.getBody().get(UserClaims.USER_FIRST_NAME.name(), String.class))
                        .lastName(jws.getBody().get(UserClaims.USER_LAST_NAME.name(), String.class))
                        .email(jws.getBody().get(UserClaims.USER_EMAIL.name(), String.class))
                        .profilePictureLink(jws.getBody().get(UserClaims.USER_PICTURE_LINK.name(), String.class))
                        .build();

                return new CustomAuthentication(userDetails);
            }
        }
        throw new RuntimeException("JWT Was not signed");
    }


    public static String authenticationToJwt(final UserDetails userDetails, final String issuer, final String subject, final int validityInSeconds) {

        final String authorities = userDetails.getAuthorities().stream()
                .map(UserAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .claim(UserClaims.USER_FIRST_NAME.name(), userDetails.getFirstName())
                .claim(UserClaims.USER_LAST_NAME.name(), userDetails.getLastName())
                .claim(UserClaims.USER_EMAIL.name(), userDetails.getEmail())
                .claim(UserClaims.USER_AUTHORITIES.name(), authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(Instant.now().plusSeconds(validityInSeconds).toEpochMilli()))
                .signWith(signatureAlgorithm,
                          TextCodec.BASE64.decode(secretKey))
                .compact();
    }

}
