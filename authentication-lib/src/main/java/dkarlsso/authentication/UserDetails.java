package dkarlsso.authentication;



import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 7247442082482524651L;

    private String email;

    private String profilePictureLink;

    private String firstName;

    private String lastName;

    private List<UserAuthority> authorities;
}
