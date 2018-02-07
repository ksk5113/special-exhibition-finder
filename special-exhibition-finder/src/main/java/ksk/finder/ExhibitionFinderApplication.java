package ksk.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackageClasses = { Jsr310JpaConverters.class }, basePackages = { "ksk.finder.exhibition.model" })
public class ExhibitionFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExhibitionFinderApplication.class, args);
	}
}
