package dkarlsso.portal;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
public class Webportal {

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(Webportal.class, args);
	}
}



