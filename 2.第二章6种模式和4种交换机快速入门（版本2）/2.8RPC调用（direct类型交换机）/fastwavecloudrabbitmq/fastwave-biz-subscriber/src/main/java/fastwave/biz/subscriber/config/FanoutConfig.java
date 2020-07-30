package fastwave.biz.subscriber.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange()
    {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Queue fanoutQueueA()
    {
        return new Queue("fanoutQueue.A", false);
    }

    @Bean
    public Queue fanoutQueueB()
    {
        return new Queue("fanoutQueue.B", false);
    }

    @Bean
    public Binding bindQueueA(FanoutExchange fanoutExchange, Queue fanoutQueueA)
    {
        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
    }

    @Bean
    public Binding bindQueueB(FanoutExchange fanoutExchange, Queue fanoutQueueB)
    {
        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
    }
}
