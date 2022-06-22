package by.goncharov.epamsound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("by.goncharov.epamsound.dao")
public class EpamsoundApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpamsoundApplication.class, args);
	}

}
