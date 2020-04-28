package fastwave.cloud.demo.fastwavebizsubscriber.config;

import org.springframework.amqp.core.*;
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
//        return new Queue("EmailQueue");
        return QueueBuilder.durable("EmailQueue")
                .withArgument("x-dead-letter-exchange", "EmailDlExchange")
                .withArgument("x-dead-letter-routing-key", "EmailDlRouting")
                .build();
    }

    @Bean
    Binding BindEmail()
    {
        return BindingBuilder.bind(EmailQueue()).to(EmailExchange()).with("EmailRouting");
    }

    @Bean
    DirectExchange EmailDlExchange()
    {
        return new DirectExchange("EmailDlExchange");
    }

    @Bean
    Queue EmailDlQueue()
    {
        return new Queue("EmailDlQueue",true);
    }

    @Bean
    Binding BindDlEmail()
    {
        return BindingBuilder.bind(EmailDlQueue()).to(EmailDlExchange()).with("EmailDlRouting");
    }

}
