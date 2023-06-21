package zti.projekt_zti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Główna klasa aplikacji Projekt ZTI.
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class ProjektZtiApplication {

	/**
	 * Metoda główna aplikacji, uruchamiająca aplikację Spring Boot.
	 *
	 * @param args Argumenty wiersza poleceń
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjektZtiApplication.class, args);
	}

}
