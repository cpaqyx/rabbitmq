package fastwave.biz.subscriber.services;

import fastwave.lib.common.config.SimpleConst;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {

    @RabbitListener(queues = SimpleConst.SIMPLE_QUEUE)
    public void receiver(String msg)
    {
        System.out.println("接收到消息:" + msg);
    }
}
