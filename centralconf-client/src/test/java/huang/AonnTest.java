package huang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AonnTest {
    public static void main(String[] args) {
        SpringApplication.run(AonnTest.class, args);
    }
}
