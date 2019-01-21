package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

	@RequestMapping("/to-read")
	public String toRead() {
		return bookService.readingList();
	}

	@RequestMapping("/to-read/v2")
	public String toReadV2() {
		return bookService2.readingListV2();
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingApplication.class, args);
	}
}
