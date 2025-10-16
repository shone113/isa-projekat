package rs.ac.uns.ftn.informatika.rest.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserMetrics {
    private final MeterRegistry registry;
    private int activeUsers = 0;

    public ActiveUserMetrics(MeterRegistry registry) {
        this.registry = registry;
        registry.gauge("active_users", this, ActiveUserMetrics::getActiveUsers);
    }

    public void userLoggedIn() { activeUsers++; }
    public void userLoggedOut() { activeUsers--; }
    public int getActiveUsers() { return activeUsers; }
}
