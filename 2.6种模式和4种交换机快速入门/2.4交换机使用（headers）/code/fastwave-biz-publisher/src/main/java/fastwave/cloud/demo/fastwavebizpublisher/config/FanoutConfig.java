package fastwave.cloud.demo.fastwavebizpublisher.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    FanoutExchange FanoutExchange()
    {
        return new FanoutExchange("FanoutExchange");
    }
}
