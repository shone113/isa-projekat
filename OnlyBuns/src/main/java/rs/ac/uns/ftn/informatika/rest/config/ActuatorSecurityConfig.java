package rs.ac.uns.ftn.informatika.rest.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Component
public class ActuatorSecurityConfig {

    @Bean
    public SecurityFilterChain actuatorSecurity(HttpSecurity http) throws Exception {
        http
                .requestMatcher(EndpointRequest.to("prometheus"))
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable();

        return http.build();
    }
}
