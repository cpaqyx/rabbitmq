package fastwave.biz.subscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {

    @RabbitListener(queues = "simpleQueue")
    public void receiver(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }
}
