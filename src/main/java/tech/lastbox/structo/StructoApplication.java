package tech.lastbox.structo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = {"tech.lastbox.structo.model", "tech.lastbox.jwt"})
public class StructoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StructoApplication.class, args);
	}

}
