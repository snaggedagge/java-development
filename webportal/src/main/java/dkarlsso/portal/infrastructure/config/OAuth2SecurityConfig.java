package dkarlsso.portal.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.Arrays;


@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret = "SIE00RcJDh6l5TcCEaahpCo3"; // TODO FIX

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId = "709826060730-kfto0lk9drosb8hskve7b2k6rehqljih.apps.googleusercontent.com"; // TODO: FIX

    private static final String KEY_GOOGLE_CLIENT_ID = "spring.security.oauth2.client.registration.google.client-id";
    private static final String KEY_GOOGLE_CLIENT_SECRET = "spring.security.oauth2.client.registration.google.client-secret";


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(Arrays.asList(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .build()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/", "/jwt/**", "/webportal/**", "/images/**", "/webjars/**", "/css/**", "/service/**", "/hotttub/**", "/api/image/**")
                .permitAll()
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/")
                .and().oauth2Login()
                .and().csrf().disable();
    }
}