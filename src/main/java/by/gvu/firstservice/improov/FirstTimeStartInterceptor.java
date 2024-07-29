package by.gvu.firstservice.improov;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FirstTimeStartInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.nanoTime();
        response.setHeader(MvcConfiguration.HEADER_TIME_START, String.valueOf(startTime));
        return true;
    }
}
