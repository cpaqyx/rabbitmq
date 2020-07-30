package fastwave.biz.subscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingReceiver {

    @RabbitListener(queues = "directQueue.warning")
    public void receiverA(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }

    @RabbitListener(queues = "directQueue.notice")
    public void receiverB(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }
}
