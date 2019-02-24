package hottub;

import dkarlsso.authentication.web.JwtLoginController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
public class WebpageApplication {

	@Bean
	public JwtLoginController jwtLoginController() {
		return new JwtLoginController();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebpageApplication.class, args);
	}
}
