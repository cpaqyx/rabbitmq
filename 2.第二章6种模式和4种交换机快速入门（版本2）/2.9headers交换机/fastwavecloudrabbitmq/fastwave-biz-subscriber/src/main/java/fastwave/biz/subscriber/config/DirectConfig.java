package fastwave.biz.subscriber.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    @Bean
    public DirectExchange DirectExchange()
    {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Queue DirectQueueA()
    {
        return new Queue("directQueue.warning", false);
    }

    @Bean
    public Queue DirectQueueB()
    {
        return new Queue("directQueue.notice", false);
    }

    @Bean
    public Binding DirectBindQueueA(DirectExchange DirectExchange, Queue DirectQueueA)
    {
        return BindingBuilder.bind(DirectQueueA).to(DirectExchange).with("warning");
    }

    @Bean
    public Binding DirectBindQueueB(DirectExchange DirectExchange, Queue DirectQueueB)
    {
        return BindingBuilder.bind(DirectQueueB).to(DirectExchange).with("notice");
    }
}

