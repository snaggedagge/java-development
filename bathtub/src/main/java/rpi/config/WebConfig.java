package rpi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rpi.SynchronizedHeaterDTO;


@Configuration
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter  {


    LoggerInterceptor loggerInterceptor = new LoggerInterceptor();

    public final SynchronizedHeaterDTO synchronizedHeaterDTO;

    public WebConfig() {
        synchronizedHeaterDTO = new SynchronizedHeaterDTO();

    }

    @Bean
    public SynchronizedHeaterDTO synchronizedHeaterDTO(){
        return synchronizedHeaterDTO;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //log.info("Adding interceptor");
        registry.addInterceptor(loggerInterceptor);
    }
}
