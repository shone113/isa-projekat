package rs.ac.uns.ftn.informatika.rest.controller;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.dto.GreetingDTO;
import rs.ac.uns.ftn.informatika.rest.dto.GreetingTextDTO;
import rs.ac.uns.ftn.informatika.rest.service.GreetingService;

import javax.servlet.http.HttpServletRequest;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@CrossOrigin(origins = "*")
public class LoadBalancerController {

	private List<String> appInstances = Arrays.asList("http://localhost:8081", "http://localhost:8082");
	private AtomicInteger counter = new AtomicInteger(0);

	@Autowired
	private RestTemplate restTemplate;  // Kreiranje RestTemplate kao bean-a

//	@PutMapping("/api/{path}")
//	@DeleteMapping("/api/{path}")
//	@PatchMapping("/api/{path}")

	@GetMapping("/api/{path1}/{path2}")
	public ResponseEntity<?> handleRequest2(
			@PathVariable String path1,
			@PathVariable String path2,
			@RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {

		String targetInstance = getNextInstance();
		String targetUrl = targetInstance + "/api/" + path1 + "/"+ path2;

//		String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
		System.out.println("PUTANJA: " + targetUrl);

		// Dodajte query parametre u URL ako ih ima
		if (!requestParams.isEmpty()) {
			String queryParams = requestParams.entrySet().stream()
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining("&"));
			targetUrl += "?" + queryParams;
		}

		System.out.println("Preusmeravanje na: " + targetUrl);

		try {
			// Preuzmi zaglavlja iz originalnog zahteva
			HttpHeaders headers = new HttpHeaders();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				headers.add(headerName, request.getHeader(headerName));
			});

			// Proveri i dodaj Authorization header ako postoji u originalnom zahtevu
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && !authHeader.isEmpty()) {
				System.out.println("POSTOJI AUTH HEADER");
				headers.set("Authorization", authHeader);
			}

			// Preuzmi telo zahteva ako postoji
			String body = null;
			if (!HttpMethod.GET.matches(request.getMethod())) {
				body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			}

			// Kreiraj HttpEntity sa telom i zaglavljima
			HttpEntity<String> entity = new HttpEntity<>(body, headers);

			// Prosledi zahtev ka ciljnim serverima
			ResponseEntity<?> response = restTemplate.exchange(
					targetUrl,
					HttpMethod.resolve(request.getMethod()),
					entity,
					Object.class
			);

			return response;

		} catch (Exception e) {
			System.err.println("Greška prilikom preusmeravanja: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Greška prilikom obrade zahteva: " + e.getMessage());
		}
	}

	@GetMapping("/api/{path1}/{path2}/{path3}")
	public ResponseEntity<?> handleRequest3(
			@PathVariable String path1,
			@PathVariable String path2,
			@PathVariable String path3,
			@RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {

		String targetInstance = getNextInstance();
		String targetUrl = targetInstance + "/api/" + path1 + "/"+ path2 + "/"+ path3;

//		String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
		System.out.println("PUTANJA: " + targetUrl);

		// Dodajte query parametre u URL ako ih ima
		if (!requestParams.isEmpty()) {
			String queryParams = requestParams.entrySet().stream()
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining("&"));
			targetUrl += "?" + queryParams;
		}

		System.out.println("Preusmeravanje na: " + targetUrl);

		try {
			// Preuzmi zaglavlja iz originalnog zahteva
			HttpHeaders headers = new HttpHeaders();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				headers.add(headerName, request.getHeader(headerName));
			});

			// Proveri i dodaj Authorization header ako postoji u originalnom zahtevu
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && !authHeader.isEmpty()) {
				System.out.println("POSTOJI AUTH HEADER");
				headers.set("Authorization", authHeader);
			}

			// Preuzmi telo zahteva ako postoji
			String body = null;
			if (!HttpMethod.GET.matches(request.getMethod())) {
				body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			}

			// Kreiraj HttpEntity sa telom i zaglavljima
			HttpEntity<String> entity = new HttpEntity<>(body, headers);

			// Prosledi zahtev ka ciljnim serverima
			ResponseEntity<?> response = restTemplate.exchange(
					targetUrl,
					HttpMethod.resolve(request.getMethod()),
					entity,
					Object.class
			);

			return response;

		} catch (Exception e) {
			System.err.println("Greška prilikom preusmeravanja: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Greška prilikom obrade zahteva: " + e.getMessage());
		}
	}


	@GetMapping("/api/{path}")
	public ResponseEntity<?> handleRequest(
			@PathVariable String path,
			@RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {

		String targetInstance = getNextInstance();
		String targetUrl = targetInstance + "/api/" + path;

//		String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
		System.out.println("PUTANJA: " + targetUrl);

		// Dodajte query parametre u URL ako ih ima
		if (!requestParams.isEmpty()) {
			String queryParams = requestParams.entrySet().stream()
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining("&"));
			targetUrl += "?" + queryParams;
		}

		System.out.println("Preusmeravanje na: " + targetUrl);

		try {
			// Preuzmi zaglavlja iz originalnog zahteva
			HttpHeaders headers = new HttpHeaders();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				headers.add(headerName, request.getHeader(headerName));
			});

			// Proveri i dodaj Authorization header ako postoji u originalnom zahtevu
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && !authHeader.isEmpty()) {
				System.out.println("POSTOJI AUTH HEADER");
				headers.set("Authorization", authHeader);
			}

			// Preuzmi telo zahteva ako postoji
			String body = null;
			if (!HttpMethod.GET.matches(request.getMethod())) {
				body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			}

			// Kreiraj HttpEntity sa telom i zaglavljima
			HttpEntity<String> entity = new HttpEntity<>(body, headers);

			// Prosledi zahtev ka ciljnim serverima
			ResponseEntity<?> response = restTemplate.exchange(
					targetUrl,
					HttpMethod.resolve(request.getMethod()),
					entity,
					Object.class
			);

			return response;

		} catch (Exception e) {
			System.err.println("Greška prilikom preusmeravanja: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Greška prilikom obrade zahteva: " + e.getMessage());
		}
	}

	@PostMapping("/api/{path}")
	public ResponseEntity<?> handlePOSTRequest(@PathVariable String path, HttpServletRequest request) {
		String targetInstance = getNextInstance();
		String targetUrl = targetInstance + "/api/" + path;
		System.out.println("POCETAK: " + targetInstance + targetUrl);

		try {
			// Preuzmi sve header-e iz originalnog zahteva
			HttpHeaders headers = new HttpHeaders();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				headers.add(headerName, request.getHeader(headerName));  // Dodaj sve header-e
			});

			// Ako treba, dodaj specifičan header za autentifikaciju (ako već nije u originalnom requestu)
			// Na primer, ako koristiš Bearer token:
			String token = "your-token-here";  // Zameni sa stvarnim tokenom ako nije u headeru
			headers.set("Authorization", "Bearer " + token);

			// Preuzmi telo zahteva ako postoji (za POST i PUT zahteve)
			HttpEntity<?> entity = null;
			if (HttpMethod.POST.equals(request.getMethod()) || HttpMethod.PUT.equals(request.getMethod())) {
				// Ako je POST ili PUT, uzmi telo zahteva (body)
				entity = new HttpEntity<>(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), headers);
			} else {
				// Ako je GET ili neki drugi metod, samo prosledi header-e
				entity = new HttpEntity<>(headers);
			}

			// Poslati zahtev sa prosleđenim header-ima i telom
			ResponseEntity<?> response = restTemplate.exchange(targetUrl, HttpMethod.resolve(request.getMethod()), entity, Object.class);
			return response;

		} catch (Exception e) {
			System.out.println("GREŠKA: " + e.getMessage());
			// Retry policy - pokušaj sa sledećom instancom
			targetInstance = getNextInstance();  // Sledeća instanca
			targetUrl = targetInstance + "/api/" + path;

			try {
				// Poslati ponovo sa novim header-ima
				HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
				return restTemplate.exchange(targetUrl, HttpMethod.resolve(request.getMethod()), entity, Object.class);
			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Greška prilikom preusmeravanja zahteva: " + targetUrl + " || "+ ex.getMessage());
			}
		}
	}



	private String getNextInstance() {
		int index = counter.getAndUpdate(i -> (i + 1) % appInstances.size());
		return appInstances.get(index);
	}

	@GetMapping("/test")
	public ResponseEntity<String> testEndpoint() {
		String response = "Ovo je odgovor sa test endpointa!";
		System.out.println("Endpoint za testiranje je uspešno pozvan!");
		return ResponseEntity.ok(response);
	}
}

