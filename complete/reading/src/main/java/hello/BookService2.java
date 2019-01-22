package hello;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import co.com.bancodebogota.RestExchange;

@Service
public class BookService2 {

	@Autowired
	private RestExchange restExchange;

	private static final Logger logger = LoggerFactory.getLogger(BookService2.class);

	@HystrixCommand(fallbackMethod = "temp", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000") })
	public ResponseEntity<BookDto> readingListV2(String x) {
		URI uri = URI.create("http://localhost:8090/recommended");
		logger.info("Calling REAL_INTEGRATION V2....");
		logger.info(x);
		return restExchange.exchange(uri.toString(), null, HttpMethod.GET, BookDto.class);

	}

	@HystrixCommand
	public ResponseEntity<BookDto> temp(String x, Throwable e) {
		logger.info("We must handle request with {} body", x);
		logger.info("Calling FALLBACK V2....");
		logger.info("Exception", e);
		return new ResponseEntity<>(new BookDto("Error Managment V2"), HttpStatus.SERVICE_UNAVAILABLE);
	}

}
