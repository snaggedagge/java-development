package dkarlsso.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 1741902233734830825L;

    private final AuthorityType authorityType;

    public UserAuthority(final AuthorityType authorityType) {
        this.authorityType = authorityType;
    }


    public static Set<UserAuthority> of(final String... authorities) {
        return Arrays.stream(authorities)
                .map(aut -> new UserAuthority(AuthorityType.valueOf(aut)))
                .collect(Collectors.toSet());
    }

    @Override
    public String getAuthority() {
        return authorityType.name();
    }
}
