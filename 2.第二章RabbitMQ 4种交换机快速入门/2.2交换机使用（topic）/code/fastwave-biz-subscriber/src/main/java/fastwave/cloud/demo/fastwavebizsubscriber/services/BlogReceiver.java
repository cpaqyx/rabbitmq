package fastwave.cloud.demo.fastwavebizsubscriber.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BlogReceiver {

    @RabbitListener(queues = "BlogJavaQueue")
    public void receiverJava(String msg)
    {
        System.out.println("收到的JAVA消息是：" + msg);
    }

    @RabbitListener(queues = "BlogDotNetQueue")
    public void receiverDotNet(String msg)
    {
        System.out.println("收到的DotNet消息是：" + msg);
    }

    @RabbitListener(queues = "BlogAllQueue")
    public void receiverAll(String msg)
    {
        System.out.println("收到的ALL消息是：" + msg);
    }
}
