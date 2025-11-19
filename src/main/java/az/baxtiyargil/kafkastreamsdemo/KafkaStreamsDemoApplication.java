package az.baxtiyargil.kafkastreamsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BatchProperties.class)
public class KafkaStreamsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsDemoApplication.class, args);
    }

}
