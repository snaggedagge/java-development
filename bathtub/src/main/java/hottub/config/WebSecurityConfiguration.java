package hottub.config;

import dkarlsso.authentication.AuthorityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .httpBasic().and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.POST).hasAnyAuthority(AuthorityType.WRITE_PRIVILEGE.name(), AuthorityType.CAN_EXECUTE_AS_ROOT.name())
                .antMatchers(HttpMethod.GET).permitAll()
                .and().formLogin().loginPage("/login/").permitAll()
                .and().logout().permitAll();
                //.and()
                //.oauth2Login();
    }
}