package playfit.se.members;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MembersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MembersApplication.class, args);
	}

}
