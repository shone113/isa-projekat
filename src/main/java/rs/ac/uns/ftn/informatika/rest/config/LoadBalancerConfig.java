package rs.ac.uns.ftn.informatika.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.informatika.rest.domain.Server;
import rs.ac.uns.ftn.informatika.rest.service.LoadBalancerService;

@Configuration
public class LoadBalancerConfig {
    @Bean
    public LoadBalancerService loadBalancer() {
        LoadBalancerService loadBalancer = new LoadBalancerService();
        loadBalancer.addServer(new Server("http://localhost:8080")); // Prva instanca
        loadBalancer.addServer(new Server("http://localhost:9090")); // Druga instanca
        return loadBalancer;
    }
}
