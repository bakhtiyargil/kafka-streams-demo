package az.baxtiyargil.kafkastreamsdemo;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.MessagingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MessagingProperties.class)
public class KafkaStreamsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsDemoApplication.class, args);
    }

}
