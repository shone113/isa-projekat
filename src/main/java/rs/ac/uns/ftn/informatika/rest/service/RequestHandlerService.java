package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Server;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestHandlerService {
    private final LoadBalancerService loadBalancer;
    private final RestTemplate restTemplate = new RestTemplate();

    public RequestHandlerService(LoadBalancerService loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public ResponseEntity<String> sendRequest(String path) {
        Server server = null;

        try {
            server = loadBalancer.getNextServer();
            server.incrementConnections(); // Povećaj broj konekcija
            System.out.println("Šaljem zahtev na: " + server.getUrl() + path);
            // Šalje zahtev serveru
            return restTemplate.getForEntity(server.getUrl() + path, String.class);
        } catch (Exception e) {
            if (server != null) {
                server.decrementConnections(); // Smanji broj konekcija ako dođe do greške
            }
            throw new RuntimeException("Zahtev nije uspeo", e);
        } finally {
            if (server != null) {
                server.decrementConnections(); // Osiguraj smanjenje konekcija
            }
        }
    }
}
