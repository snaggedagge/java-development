package dkarlsso.portal.infrastructure.config.interceptor;




import com.nimbusds.oauth2.sdk.util.StringUtils;
import dkarlsso.portal.domain.model.Container;
import dkarlsso.portal.domain.model.UserRepository;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Configuration
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    private final Container<UserInfo> userInfo = new Container<>();

    @Bean
    public Container getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,final Object handler) throws Exception {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null
                && authentication.getPrincipal() instanceof DefaultOidcUser) {
            final DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

            final String email = oidcUser.getEmail();
            if(StringUtils.isNotBlank(email)) {
                userInfo.setValue(userRepository.findByEmail(email).orElseGet(UserInfo::new));
                session.setAttribute("user", userInfo.getValue());
                return true;
            }
        }

        userInfo.setValue(new UserInfo());
        session.setAttribute("user", userInfo.getValue());
        return true;
    }


}