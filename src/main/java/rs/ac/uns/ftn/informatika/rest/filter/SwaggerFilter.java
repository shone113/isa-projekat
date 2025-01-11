package rs.ac.uns.ftn.informatika.rest.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SwaggerFilter extends OncePerRequestFilter {

    // Lista URL putanja koje treba isključiti iz filtera (npr. Swagger)
    private List<String> excludeUrlPatterns = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Ako trenutni URL treba biti isključen, preskoči dalje filtere
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Ovde ide tvoj kod za load balancing i dalje obrade, ako URL nije u exclude listi
        // Npr. pozivanje load balancera
        // (Ovaj deo je sličan tvojoj originalnoj implementaciji)

        filterChain.doFilter(request, response);  // Prosleđivanje zahteva dalje
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Provera da li URL zahteva spada u one koje treba isključiti
        String requestUri = request.getRequestURI();
        return excludeUrlPatterns.stream().anyMatch(requestUri::startsWith);
    }
}
