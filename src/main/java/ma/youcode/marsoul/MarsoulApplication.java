package ma.youcode.marsoul;

import ma.youcode.marsoul.constant.FileConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class MarsoulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsoulApplication.class, args);
		new File(FileConstant.USER_FOLDER).mkdirs();
	}

}
