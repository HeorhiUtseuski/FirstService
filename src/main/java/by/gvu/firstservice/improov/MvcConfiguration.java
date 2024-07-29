package by.gvu.firstservice.improov;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration  implements WebMvcConfigurer {
    public final static String HEADER_TIME_START = "time-start";
    public final static String HEADER_TIME_END = "time-end";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FirstTimeStartInterceptor());
    }
}
