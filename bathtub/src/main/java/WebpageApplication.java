import com.pi4j.io.gpio.GpioFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
public class WebpageApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
		GpioFactory.getInstance();
		return builder.sources(WebpageApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebpageApplication.class, args);
	}
}
