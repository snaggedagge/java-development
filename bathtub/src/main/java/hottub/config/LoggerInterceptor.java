package hottub.config;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoggerInterceptor extends HandlerInterceptorAdapter {


    Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
    Logger localLog = LoggerFactory.getLogger("local-connections");


    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,final Object handler) throws Exception {

        String ipAddr = getRemoteAddr(request);

        if(ipAddr.contains("127.0") || ipAddr.contains("0:0:0") ||  ipAddr.contains("192.168.1")) {
            localLog.info("Requestlogger : " + " Method: [" + request.getMethod()
                    + "] URL: [" + request.getRequestURI()+"]" );
        } else {
            log.info("Requestlogger : " + " Method: [" + request.getMethod()
                    + "] URL: [" + request.getRequestURI()+"] Ipadress: [" + ipAddr+"]" );
        }

        return true;
    }

    private String getRemoteAddr(final HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }


}