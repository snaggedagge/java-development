package hottub.config;


import com.pi4j.io.gpio.GpioFactory;
import dkarlsso.commons.raspberry.OSHelper;
import hottub.repository.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import hottub.model.HeaterDataDTO;


@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor = new LoggerInterceptor();

    private final HeaterDataDTO heaterDataDTO;

    private final SettingsService settingsService;

    @Autowired
    public WebConfig(final SettingsService settingsService) {
        if (OSHelper.isRaspberryPi()) {
            GpioFactory.getInstance();
        }
        heaterDataDTO = new HeaterDataDTO();
        heaterDataDTO.applySettings(settingsService.getSettings());
        this.settingsService = settingsService;
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
