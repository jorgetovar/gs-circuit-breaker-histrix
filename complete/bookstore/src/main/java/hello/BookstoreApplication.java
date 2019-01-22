package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BookstoreApplication {

	private static final Logger logger = LoggerFactory.getLogger(BookstoreApplication.class);

	@RequestMapping(value = "/recommended")
	public ResponseEntity<BookDto> readingList() throws InterruptedException {
		Thread.sleep(2000);
		logger.info("ESB CALL ....");
		String title = "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
		return new ResponseEntity<>(new BookDto(title), HttpStatus.CONFLICT);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
