package az.baxtiyargil.kafkastreamsdemo.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(value = "application.messaging")
public class MessagingProperties {

    String maxDltRetry;

}
