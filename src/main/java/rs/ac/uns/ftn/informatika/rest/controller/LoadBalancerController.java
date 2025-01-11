package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.informatika.rest.domain.Server;
import rs.ac.uns.ftn.informatika.rest.service.LoadBalancerService;
import rs.ac.uns.ftn.informatika.rest.service.RequestHandlerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class LoadBalancerController {
    private final LoadBalancerService loadBalancer; // Klasa koja sadrži algoritam (Round Robin)
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public LoadBalancerController(LoadBalancerService loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
    // Lista URL obrazaca koje treba obraditi bez load balancera
    private List<String> excludeUrlPatterns = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars"
    );

    @RequestMapping("/**")
    public ResponseEntity<Object> handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();

        for (String pattern : excludeUrlPatterns) {
            if (requestUri.startsWith(pattern)) {
                try {
                    // Prosledi zahtev ka odgovarajućem resursu (npr. Swagger) bez load balancera
                    request.getRequestDispatcher(requestUri).forward(request, response);
                    return null;  // Povratak odmah nakon što je zahtev prosleđen
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri prosleđivanju.");
                }
            }
        }

        // Preko flag-a proveriti da li je zahtev već prosleđen
        if (request.getAttribute("forwarded") != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Zahtev je već prosleđen.");
        }

        try {
            // Dobijanje trenutnog servera prema Round Robin algoritmu
            Server server = loadBalancer.getNextServer();

            // Konstruisanje URL-a za prosleđivanje zahteva
            String fullUrl = server.getUrl() + request.getRequestURI();

            // Presretanje metoda (GET, POST, itd.)
            HttpMethod method = HttpMethod.valueOf(request.getMethod());

            // Kopiranje zaglavlja
            HttpHeaders headers = new HttpHeaders();
            Collections.list(request.getHeaderNames())
                    .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));

            // Kopiranje tela zahteva (za POST, PUT itd.)
            HttpEntity<Object> entity = new HttpEntity<>(request.getInputStream(), headers);

            // Prosleđivanje zahteva
            ResponseEntity<Object> newResponse = restTemplate.exchange(fullUrl, method, entity, Object.class);

            return newResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška u Load Balancer-u");
        }
    }


}
