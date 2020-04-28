package fastwave.cloud.demo.fastwavebizsubscriber.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HeadersConfig {

    @Bean
    HeadersExchange headersExchange()
    {
        return new HeadersExchange("HeadersExchange");
    }


    @Bean
    Queue headerQueue1()
    {
        return new Queue("headerQueue1");
    }

    @Bean
    Queue headerQueue2()
    {
        return new Queue("headerQueue2");
    }

    @Bean
    Queue headerQueue3()
    {
        return new Queue("headerQueue3");
    }

    @Bean
    Queue headerQueue4()
    {
        return new Queue("headerQueue4");
    }

    @Bean
    Binding bindHeaderQueue1()
    {
        return BindingBuilder.bind(headerQueue1()).to(headersExchange())
                .whereAll("token", "id").exist();
    }

    @Bean
    Binding bindHeaderQueue2()
    {
        return BindingBuilder.bind(headerQueue2()).to(headersExchange())
                .whereAny("token", "id").exist();
    }

    @Bean
    Binding bindHeaderQueue3()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", "123");
        map.put("id", "123");
        return BindingBuilder.bind(headerQueue3()).to(headersExchange())
                .whereAll(map).match();
    }

    @Bean
    Binding bindHeaderQueue4()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", "123");
        map.put("id", "123");
        return BindingBuilder.bind(headerQueue4()).to(headersExchange())
                .whereAny(map).match();
    }
}
