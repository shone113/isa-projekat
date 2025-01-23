package rs.ac.uns.ftn.informatika.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import rs.ac.uns.ftn.informatika.rest.security.auth.TokenAuthenticationFilter;
import rs.ac.uns.ftn.informatika.rest.service.impl.CustomUserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import rs.ac.uns.ftn.informatika.rest.util.TokenUtils;
import rs.ac.uns.ftn.informatika.rest.security.auth.RestAuthenticationEntryPoint;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// Injektovanje bean-a za bezbednost
@EnableWebSecurity

// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService()) // Make sure your service is correctly implemented
                .passwordEncoder(passwordEncoder()) // Check if you're using the right encoder
                .and()
                .build();
    }
//@Bean
//public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//}

    @Autowired
    private TokenUtils tokenUtils;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // svim korisnicima dopusti da pristupe sledecim putanjama:
//        // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
//        // ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru
//        // ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
//        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login").permitAll().antMatchers("/auth/**").permitAll()		// /auth/**
//                .antMatchers(HttpMethod.GET, "/api/post").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/profile/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/api/profile/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/api/comment//by-post-id/{id}").permitAll()
//                .antMatchers("/socket/**", "/socket-endpoint/**", "/webjars/**").permitAll()
//                //.antMatchers("/api/user/{id}").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/api/user/**").permitAll()
//                .antMatchers("/user/").permitAll()
//                .antMatchers("/api/chat/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/user/id").permitAll()// Dozvoli USER i ADMIN
//                .antMatchers("/h2-console/**").permitAll()	// /h2-console/** ako se koristi H2 baza)
//                .antMatchers("/api/foo").permitAll()		// /api/foo
//                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**").permitAll()
//                // ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
//                // koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
//                // samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin:
//                // .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
//
//                // za svaki drugi zahtev korisnik mora biti autentifikovan
//                .anyRequest().authenticated().and()
//                // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
//                .cors().and().csrf().and()
//                .formLogin().loginPage("/login").permitAll().and()
//                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);
//
//        // zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
//        http.csrf().disable();
//
//        // ulancavanje autentifikacije
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RestAuthenticationEntryPoint restAuthenticationEntryPoint) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/api/**"), new AntPathRequestMatcher("/v3/api-docs/**"), new AntPathRequestMatcher("/swagger-ui/**"), new AntPathRequestMatcher("http://localhost:4200")).permitAll() // Precizno definišete rute
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
       return (web) -> web.ignoring().antMatchers(HttpMethod.POST, "/auth/login")

                // Ovim smo dozvolili pristup statickim resursima aplikacije
                .antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico",
                        "/**/*.html", "/**/*.css", "/**/*.js");

    }
}
