package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Server;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadBalancerService {
    private final List<Server> servers = new ArrayList<>();

    // Dodaj servere
    public void addServer(Server server) {
        servers.add(server);
    }

    // Pronalaženje servera sa najmanje aktivnih konekcija
    public synchronized Server getNextServer() {
//        return servers.stream()
//                .min((s1, s2) -> Integer.compare(s1.getActiveConnections(), s2.getActiveConnections()))
//                .orElseThrow(() -> new RuntimeException("Nema dostupnih servera"));
        Server server = servers.stream()
                .min((s1, s2) -> Integer.compare(s1.getActiveConnections(), s2.getActiveConnections()))
                .orElseThrow(() -> new RuntimeException("Nema dostupnih servera"));

        // Ispis na konzolu
        System.out.println("Zahtev je prosleđen na server: " + server.getUrl());

        return server;
    }
}
