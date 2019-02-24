package dkarlsso.smartmirror.spring;


import dkarlsso.authentication.web.JwtLoginController;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class SmartMirrorWebApplication {

	@Bean
	public SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}

//	@Bean
//	public LayoutDialect layoutDialect() {
//		return new LayoutDialect();
//	}

	@Bean
	public JwtLoginController jwtLoginController() {
		return new JwtLoginController();
	}


	public static void main(String[] args) {
		SpringApplication.run(SmartMirrorWebApplication.class, args);
	}
}
