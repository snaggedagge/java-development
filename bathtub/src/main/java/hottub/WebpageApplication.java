package hottub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
public class WebpageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebpageApplication.class, args);
	}
}
