package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import co.com.bancodebogota.RestExchange;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ReadingApplication {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookService2 bookService2;

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public RestExchange restExchange() {
		return new RestExchange();
	}

	@RequestMapping("/to-read")
	public ResponseEntity<BookDto> toRead() {
		return bookService.readingList();
	}

	@RequestMapping("/to-read/v2")
	public ResponseEntity<BookDto> toReadV2() {
		return bookService2.readingListV2("TestInputVariable");
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingApplication.class, args);
	}
}
