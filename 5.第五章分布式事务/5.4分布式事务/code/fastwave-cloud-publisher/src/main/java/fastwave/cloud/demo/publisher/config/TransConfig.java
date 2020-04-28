package fastwave.cloud.demo.publisher.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransConfig {

    public static final String TRANSFER_EXCHANGE_NAME = "TransExchange";
    public static final String TRANSFER_ROUTING_KEY = "TransRoutingKey";

    @Bean
    public DirectExchange TransExchange(){
        return new DirectExchange(TRANSFER_EXCHANGE_NAME);
    }
}
