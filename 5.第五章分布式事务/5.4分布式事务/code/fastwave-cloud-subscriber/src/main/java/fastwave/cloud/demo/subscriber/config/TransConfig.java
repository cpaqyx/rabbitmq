package fastwave.cloud.demo.subscriber.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransConfig {

    public static final String TRANSFER_EXCHANGE_NAME = "TransExchange";
    public static final String TRANSFER_ROUTING_KEY = "TransRoutingKey";
    public static final String TRANSFER_QUEUE_NAME = "TransDirectQueue";

    public static final String TRANSFER_DL_EXCHANGE_NAME = "TransDlExchange";
    public static final String TRANSFER_DL_ROUTING_KEY = "TransDlRoutingKey";
    public static final String TRANSFER_DL_QUEUE_NAME = "TransDlDirectQueue";

    @Bean
    public DirectExchange TransExchange(){
        return new DirectExchange(TRANSFER_EXCHANGE_NAME);
    }

    @Bean
    public Queue TransQueue() {
        return QueueBuilder.durable(TRANSFER_QUEUE_NAME)
                //配置死信
                .withArgument("x-dead-letter-exchange",TRANSFER_DL_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key",TRANSFER_DL_ROUTING_KEY)
                .build();
    }

    @Bean
    Binding bindingTrans() {
        return BindingBuilder.bind(TransQueue()).to(TransExchange()).with(TRANSFER_ROUTING_KEY);
    }

    @Bean
    public DirectExchange TransDlExchange(){
        return new DirectExchange(TRANSFER_DL_EXCHANGE_NAME);
    }

    @Bean
    public Queue TransDlQueue() {
        return new Queue(TRANSFER_DL_QUEUE_NAME,true);  //true 是否持久
    }

    @Bean
    Binding bindingDlTrans() {
        return BindingBuilder.bind(TransQueue()).to(TransExchange()).with(TRANSFER_DL_ROUTING_KEY);
    }
}
