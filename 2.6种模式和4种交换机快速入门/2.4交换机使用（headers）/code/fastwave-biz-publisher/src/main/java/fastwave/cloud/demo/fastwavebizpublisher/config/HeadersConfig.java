package fastwave.cloud.demo.fastwavebizpublisher.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeadersConfig {

    @Bean
    HeadersExchange headersExchange()
    {
        return new HeadersExchange("HeadersExchange");
    }
}
