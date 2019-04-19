package dkarlsso.portal.infrastructure.config.interceptor;


import dkarlsso.portal.domain.model.Container;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Container<UserInfo> userInfo;

    @Autowired
    private SessionInterceptor sessionInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //log.info("Adding interceptor");
        registry.addInterceptor(sessionInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserArgumentResolver(userInfo));
    }



}
