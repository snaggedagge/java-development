package dkarlsso.smartmirror.spring.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthProvider;

/*    @Autowired
    private Environment env;

    private static List<String> clients = Arrays.asList("google");
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .httpBasic().and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/","/webjars/**", "/login/**", "/login/**")
                .permitAll()
                .anyRequest().hasRole("USER")
                .and().formLogin().loginPage("/login/").permitAll()
                .and().logout().permitAll();
                //.and()
                //.oauth2Login();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
/*        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder().encode("pass"))
                .roles("USER");*/
    }


/*    private static String CLIENT_PROPERTY_KEY
            = "spring.security.oauth2.client.registration.";


    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }*/

}