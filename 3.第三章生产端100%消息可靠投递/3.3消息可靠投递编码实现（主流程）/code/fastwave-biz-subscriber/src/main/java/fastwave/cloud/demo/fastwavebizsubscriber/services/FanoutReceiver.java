package fastwave.cloud.demo.fastwavebizsubscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

    @RabbitListener(queues = "FanoutQueue")
    public void receiver(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }

    @RabbitListener(queues = "FanoutQueue2")
    public void receiver2(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }
}
