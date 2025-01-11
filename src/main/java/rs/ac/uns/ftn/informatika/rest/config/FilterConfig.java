package rs.ac.uns.ftn.informatika.rest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.informatika.rest.filter.SwaggerFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<SwaggerFilter> loggingFilter() {
        FilterRegistrationBean<SwaggerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SwaggerFilter());
        registrationBean.addUrlPatterns("/**");  // Primeni na sve URL putanje
        return registrationBean;
    }
}
