package fastwave.biz.subscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {

    @RabbitListener(queues = "IT.java")
    public void receiverA(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }

    @RabbitListener(queues = "IT.DotNet")
    public void receiverB(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }

    @RabbitListener(queues = "rabbitMQ")
    public void receiverC(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }
}
