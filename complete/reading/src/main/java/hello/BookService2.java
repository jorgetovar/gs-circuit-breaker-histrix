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
public class BookService2 {

	private final RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(BookService2.class);

	public BookService2(RestTemplate rest) {
		this.restTemplate = rest;
	}

	@HystrixCommand(fallbackMethod = "temp", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000") })
	public ResponseEntity<String> readingListV2(String x) {
		URI uri = URI.create("http://localhost:8090/recommended");
		logger.info("Calling REAL_INTEGRATION V2....");
		logger.info(x);
		return this.restTemplate.getForEntity(uri, String.class);

	}
	
	@HystrixCommand
	public ResponseEntity<String> temp(String x, Throwable e) {
		logger.info("We must handle request with {} body", x);
		logger.info("Calling FALLBACK V2....");
		logger.info("Exception", e);
		return new ResponseEntity<>("Error V2", HttpStatus.SERVICE_UNAVAILABLE);
	}

}
