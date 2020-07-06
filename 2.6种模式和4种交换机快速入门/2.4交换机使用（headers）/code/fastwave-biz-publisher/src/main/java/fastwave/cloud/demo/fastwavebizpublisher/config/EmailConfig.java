package fastwave.cloud.demo.fastwavebizpublisher.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    DirectExchange EmailExchange()
    {
        return new DirectExchange("EmailExchange");
    }
}
