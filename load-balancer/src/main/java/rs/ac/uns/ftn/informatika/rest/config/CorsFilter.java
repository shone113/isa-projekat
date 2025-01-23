//package rs.ac.uns.ftn.informatika.rest.config;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CorsFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//
//        chain.doFilter(request, response);
//    }
//}
