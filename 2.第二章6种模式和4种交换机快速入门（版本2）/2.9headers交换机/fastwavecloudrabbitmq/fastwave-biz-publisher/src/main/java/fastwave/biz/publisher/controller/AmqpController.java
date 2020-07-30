package fastwave.biz.publisher.controller;

import fastwave.lib.common.config.SimpleConst;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/amqp")
public class AmqpController {

    @Autowired
    RabbitTemplate template;

    @GetMapping("/simple")
    public String simple(@RequestParam Map<String, String> params)
    {
        String msg = params.get("msg");
        template.convertAndSend(SimpleConst.SIMPLE_QUEUE, msg);
        return "OK";
    }

    @GetMapping("/publish")
    public String publish(@RequestParam Map<String, String> params)
    {
        String msg = params.get("msg");
        template.convertAndSend("fanoutExchange",null, msg);
        return "OK";
    }

    @GetMapping("/routing")
    public String routing(@RequestParam Map<String, String> params)
    {
        String msg = params.get("msg");
        String level = params.get("level");
        template.convertAndSend("directExchange",level, msg);
        return "OK";
    }

    /**
     * IT
     *  java
     *      spring
     *          spring ioc
     *          spring cloud
     *
     *      rabbitmq
     *  dotNet
     *  python
     *
     *  IT.java.#
     */
    @GetMapping("/topic")
    public String topic(@RequestParam Map<String, String> params)
    {
        String msg = params.get("msg");
        String topic = params.get("topic");
        template.convertAndSend("TopicExchange",topic, msg);
        return "OK";
    }

    @GetMapping("/rpc")
    public String rpc(@RequestParam Map<String, String> params)
    {
        String msg = params.get("msg");
        String level = params.get("level");

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message((msg).getBytes(),messageProperties);
        template.setReplyTimeout(3000);
        Message reply = template.sendAndReceive("directExchange",level, message);

        System.out.println(reply);
        System.out.println("消费端返回结果:"+new String(reply.getBody()));
        System.out.println("消费端消息属性:"+reply.getMessageProperties());
        return "OK";
    }

}
