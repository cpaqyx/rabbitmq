package fastwave.biz.publisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public TopicExchange TopicExchange()
    {
        return new TopicExchange("TopicExchange");
    }

    @Bean
    public Queue TopicQueueJava()
    {
        return new Queue("IT.java", false);
    }

    @Bean
    public Queue TopicQueueRabbitMQ()
    {
        return new Queue("rabbitMQ", false);
    }

    @Bean
    public Queue TopicQueueDotNet()
    {
        return new Queue("IT.DotNet", false);
    }

    @Bean
    public Binding TopicBindQueueJava(TopicExchange TopicExchange, Queue TopicQueueJava)
    {
        return BindingBuilder.bind(TopicQueueJava).to(TopicExchange).with("IT.java.#");
    }

    @Bean
    public Binding TopicBindQueueDotNet(TopicExchange TopicExchange, Queue TopicQueueDotNet)
    {
        return BindingBuilder.bind(TopicQueueDotNet).to(TopicExchange).with("IT.dotNet.*");
    }

    @Bean
    public Binding TopicBindQueueRabbitMQ(TopicExchange TopicExchange, Queue TopicQueueRabbitMQ)
    {
        return BindingBuilder.bind(TopicQueueRabbitMQ).to(TopicExchange).with("#.rabbitMQ");
    }
}
