package fastwave.cloud.demo.fastwavebizsubscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    @RabbitListener(queues = "EmailQueue")
    public void receiver(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }
}
