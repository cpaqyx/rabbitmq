package fastwave.cloud.demo.fastwavebizpublisher.config;


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

}
