package fastwave.cloud.demo.fastwavebizsubscriber.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogConfig {

    @Bean
    TopicExchange BlogExchange()
    {
        return  new TopicExchange("BlogExchange");
    }

    @Bean
    Queue BlogJavaQueue()
    {
        return new Queue("BlogJavaQueue", true);
    }

    @Bean
    Queue BlogDotNetQueue()
    {
        return new Queue("BlogDotNetQueue", true);
    }

    @Bean
    Queue BlogAllQueue()
    {
        return new Queue("BlogAllQueue", true);
    }

    @Bean
    Binding BindingToJavaQueue()
    {
        return BindingBuilder.bind(BlogJavaQueue()).to(BlogExchange()).with("blog.java");
    }

    @Bean
    Binding BindingToDotNetQueue()
    {
        return BindingBuilder.bind(BlogDotNetQueue()).to(BlogExchange()).with("blog.dotNet");
    }

    @Bean
    Binding BindingToAllQueue()
    {
        return BindingBuilder.bind(BlogAllQueue()).to(BlogExchange()).with("blog.#");
    }
}
