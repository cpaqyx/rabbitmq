package fastwave.cloud.demo.fastwavebizsubscriber.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    DirectExchange EmailExchange()
    {
        return new DirectExchange("EmailExchange");
    }

    @Bean
    Queue EmailQueue()
    {
        return new Queue("EmailQueue");
    }

    @Bean
    Binding BindEmail()
    {
        return BindingBuilder.bind(EmailQueue()).to(EmailExchange()).with("EmailRouting");
    }

}
