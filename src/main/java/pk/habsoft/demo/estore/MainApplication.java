package pk.habsoft.demo.estore;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class EStoreApp.
 */
@SpringBootApplication
public class MainApplication {

	private static final Logger LOG = Logger.getLogger(MainApplication.class);

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
