package hello;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookService {

	private final RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	public BookService(RestTemplate rest) {
		this.restTemplate = rest;
	}

	@HystrixCommand(fallbackMethod = "reliable", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000") })
	public ResponseEntity<String> readingList() {
		URI uri = URI.create("http://localhost:8090/recommended");
		logger.info("Calling V1....");
		return this.restTemplate.getForEntity(uri, String.class);
	}

	public ResponseEntity<String> temp() {
		logger.info("Calling FALLBACK V1....");
		return new ResponseEntity<>("Error V1", HttpStatus.SERVICE_UNAVAILABLE);
	}

}
