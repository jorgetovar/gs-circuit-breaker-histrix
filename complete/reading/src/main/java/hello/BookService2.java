package hello;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000")	
	})
	public String readingListV2() {
		URI uri = URI.create("http://localhost:8090/recommended");
		logger.info("Calling V2....");
		return this.restTemplate.getForObject(uri, String.class);
	}

	public String temp() {
		logger.info("Calling FALLBACK V2....");
		return "Cloud Native Java (O'Reilly) V2";
	}

}
