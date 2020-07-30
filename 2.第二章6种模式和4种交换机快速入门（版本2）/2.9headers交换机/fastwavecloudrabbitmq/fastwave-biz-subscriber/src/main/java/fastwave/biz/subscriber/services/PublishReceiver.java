package fastwave.biz.subscriber.services;

import fastwave.lib.common.config.SimpleConst;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PublishReceiver {

    @RabbitListener(queues = "fanoutQueue.A")
    public void receiverA(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }

    @RabbitListener(queues = "fanoutQueue.B")
    public void receiverB(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }
}
