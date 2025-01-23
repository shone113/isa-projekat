//package rs.ac.uns.ftn.informatika.rest.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Allow all paths
//                .allowedOrigins("http://localhost:4200", "http://localhost:8081", "http://localhost:8082") // Allow requests from localhost:4200 (Angular)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific methods
//                .allowedHeaders("*") // Allow all headers
//                .allowCredentials(true);
//    }
//}
