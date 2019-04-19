//
//package dkarlsso.portal.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
////
////    @Bean
////    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
////        StrictHttpFirewall firewall = new StrictHttpFirewall();
////        firewall.setAllowUrlEncodedSlash(true);
////        firewall.setAllowBackSlash(true);
////        firewall.setAllowSemicolon(true);
////        firewall.setUnsafeAllowAnyHttpMethod(true);
////        return firewall;
////    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//
//        final UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
//        auth
//                .inMemoryAuthentication()
//                .withUser(user);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers( "/", "/webportal/**", "/login/**", "/webjars/**", "/css/**", "/service/**", "/hotttub/**", "/api/image/**")
//                    .permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                // TODO: remove this
//                .csrf().disable();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("user").password("pass")
////                .authorities("ROLE_USER");
////    }
////
//////
//////    @Autowired
//////    private UserDetailsService userDetailsService;
////
//////    @Bean
//////    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////
////
////        http.authorizeRequests()
////                .antMatchers( "/", "/home", "/login/**", "/webjars/bootstrap/**", "/css/**").permitAll()
//////                .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
////                .anyRequest().authenticated();
//////                .and()
//////                .formLogin().loginPage("/login")
//////                .failureUrl("/login?error=true")
//////                .defaultSuccessUrl("/login/loginSuccess")
//////                .usernameParameter("username").passwordParameter("password")
//////                .and()
//////                .logout().logoutSuccessUrl("/login?logout=true")
//////                .and()
//////                .exceptionHandling().accessDeniedPage("/403")
//////                .and()
//////                .csrf();
////    }
////
//////
//////    @Autowired
//////    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//////        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//////    }
//}
//
