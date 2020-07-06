package fastwave.cloud.demo.fastwavebizsubscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HeaderReceiver {

    @RabbitListener(queues = "headerQueue1")
    public void receiver(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }

    @RabbitListener(queues = "headerQueue2")
    public void receiver2(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }

    @RabbitListener(queues = "headerQueue3")
    public void receiver3(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }

    @RabbitListener(queues = "headerQueue4")
    public void receiver4(String msg)
    {
        System.out.println("收到的消息是：" + msg);
    }
}
