package ma.youcode.marsoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class MarsoulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsoulApplication.class, args);
	}

}
