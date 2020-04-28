package fastwave.cloud.demo.publisher;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;


//@RunWith(SpringRunner.class)
//classes属性指定启动类
//@SpringBootTest(classes = FastwaveCloudPublisherApplication.class)
public class RabbitmqApplicationTests {

//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Test
//    public void testAdmin() {
//        //创建交换机
//        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
//        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
//        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));
//        //创建队列
//        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
//        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
//        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));
//        //创建绑定关系 Binding构造函数的参数 队列名称、绑定类型、交换机名称、绑定键
//        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
//                Binding.DestinationType.QUEUE,
//                "test.direct",
//                "direct",
//                new HashMap<>()));
//        rabbitAdmin.declareBinding(
//                BindingBuilder.
//                        bind(new Queue("test.topic.queue", false))
//                        .to(new TopicExchange("test.topic", false, false))
//                        .with("user.#"));
//        rabbitAdmin.declareBinding(
//                BindingBuilder.
//                        bind(new Queue("test.fanout.queue", false))
//                        .to(new FanoutExchange("test.fanout", false, false)));
//        //清空队列数据
//        rabbitAdmin.purgeQueue("test.topic.queue", false);
//    }
//
//    @Test
//    public void testSendMessage() {
//        //创建消息
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.getHeaders().put("desc", "信息描述");
//        messageProperties.getHeaders().put("type", "自定义消息类型");
//        Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);
//        //发送消息
//        rabbitTemplate.convertAndSend("test.topic", "spring.amqp", message, new MessagePostProcessor() {
//            //在执行消息转换后添加/修改标题或属性然后在进行发送
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
//                message.getMessageProperties().getHeaders().put("attr", "额外新添加的属性");
//                return message;
//            }
//        });
//    }

}
