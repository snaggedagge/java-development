package dkarlsso.portal.infrastructure.config.interceptor;

import dkarlsso.portal.domain.model.Container;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final Container<UserInfo> userInfo;

    public UserArgumentResolver(final Container<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        return this.userInfo.getValue();
    }
}
