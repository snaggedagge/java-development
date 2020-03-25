package dkarlsso.commons.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Entity(name = "oauth2_credential")
public class Oauth2Credential {

    @Id
    private String userEmail;

    @Column
    private String oauth2Scopes;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Column
    private long expiresIn;

//    public List<Oauth2Scope> oauth2Scopes() {
//        return Arrays.stream(oauth2Scopes.split(","))
//                .map(Oauth2Scope::valueOf)
//                .collect(Collectors.toList());
//    }
//
//    public void setOauth2Scopes(String oauth2Scopes) {
//        this.oauth2Scopes = oauth2Scopes;
//    }
//
//    public void setOauth2Scopes(final List<Oauth2Scope> oauth2Scopes) {
//        this.oauth2Scopes = oauth2Scopes.stream()
//                .map(Enum::name)
//                .collect(Collectors.joining(","));
//    }
//
//    public GoogleCredential getGoogleCredential() {
//        final GoogleCredential credential = new GoogleCredential();
//        credential.setAccessToken(this.getAccessToken());
//        credential.setExpiresInSeconds(this.getExpiresIn());
//        credential.setRefreshToken(this.getRefreshToken());
//        return credential;
//    }
}
