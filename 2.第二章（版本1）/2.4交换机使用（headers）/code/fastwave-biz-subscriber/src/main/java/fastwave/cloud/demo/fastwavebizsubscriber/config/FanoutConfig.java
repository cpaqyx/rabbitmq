package fastwave.cloud.demo.fastwavebizsubscriber.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    FanoutExchange FanoutExchange()
    {
        return new FanoutExchange("FanoutExchange");
    }


    @Bean
    Queue FanoutQueue()
    {
        return new Queue("FanoutQueue");
    }

    @Bean
    Queue FanoutQueue2()
    {
        return new Queue("FanoutQueue2");
    }

    @Bean
    Binding BindFanout()
    {
        return BindingBuilder.bind(FanoutQueue()).to(FanoutExchange());
    }

    @Bean
    Binding BindFanout2()
    {
        return BindingBuilder.bind(FanoutQueue2()).to(FanoutExchange());
    }

}
