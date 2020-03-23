package dkarlsso.smartmirror.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@ComponentScan("dkarlsso")
@EnableScheduling
@EntityScan("dkarlsso")
public class SmartMirrorWebApplication {

	@Bean
	public SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}

//	@Bean
//	public LayoutDialect layoutDialect() {
//		return new LayoutDialect();
//	}

	public static void main(String[] args) {
		SpringApplication.run(SmartMirrorWebApplication.class, args);
	}
}
