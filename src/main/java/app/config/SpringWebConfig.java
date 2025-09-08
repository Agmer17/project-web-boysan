package app.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.middleware.AdminAuthenticationInterceptor;
import app.middleware.JwtAuthInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtAuthInterceptor jwtInterceptor;

    @Autowired
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/", "/home", "/login", "/sign-in", "/auth/**", "/css/**", "/js/**", "/img/**", "/uploads/**");
        registry.addInterceptor(adminAuthenticationInterceptor).addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = Paths.get("uploads").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/**").addResourceLocations(uploadDir);
    }
}
