package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.middleware.JwtAuthInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtAuthInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns(
                "/api/profile/**", "/live-chat/**");
    }
}
