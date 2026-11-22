package ru.sqltrainer.sql_trainer.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sqltrainer.sql_trainer.security.PasswordChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final PasswordChangeInterceptor passwordChangeInterceptor;

    public WebMvcConfig(PasswordChangeInterceptor passwordChangeInterceptor) {
        this.passwordChangeInterceptor = passwordChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passwordChangeInterceptor);
    }
}
