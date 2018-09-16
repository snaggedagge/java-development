package hottub.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import hottub.model.HeaterDataDTO;


@Configuration
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter  {

    private final LoggerInterceptor loggerInterceptor = new LoggerInterceptor();

    private final HeaterDataDTO heaterDataDTO;

    public WebConfig() {
        heaterDataDTO = new HeaterDataDTO();
    }

    @Bean
    public HeaterDataDTO synchronizedHeaterDTO(){
        return heaterDataDTO;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //log.info("Adding interceptor");
        registry.addInterceptor(loggerInterceptor);
    }
}