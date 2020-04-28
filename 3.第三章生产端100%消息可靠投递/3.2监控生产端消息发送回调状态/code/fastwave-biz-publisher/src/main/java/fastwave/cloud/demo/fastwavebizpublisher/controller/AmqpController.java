package fastwave.cloud.demo.fastwavebizpublisher.controller;

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
@RequestMapping("amqp")
public class AmqpController {

    @Autowired
    RabbitTemplate template;

    @GetMapping("/direct")
    public String sendEmail(@RequestParam Map<String, Object> params)
    {
        try
        {
            String msg = params.get("msg").toString();
            template.convertAndSend("EmailExchange", "EmailRouting", msg);
            return "OK";
        }
        catch (Exception e)
        {
            return "网络中断，请稍后重试";
        }

    }

    @GetMapping("/noExchange")
    public String noExchange(@RequestParam Map<String, Object> params)
    {
        try
        {
            String msg = params.get("msg").toString();
            String uuid = UUID.randomUUID().toString();
            template.convertAndSend("EmailExchange", "EmailRouting2", msg, new CorrelationData(uuid));
            return "OK";
        }
        catch (Exception e)
        {
            return "网络中断，请稍后重试";
        }

    }

    @GetMapping("/topic")
    public String sendBlog(@RequestParam Map<String, Object> params)
    {
        String msg = params.get("msg").toString();
        String routingKey = params.get("key").toString();
        template.convertAndSend("BlogExchange", routingKey, msg);
        return "OK";
    }

    @GetMapping("/fanout")
    public String sendFanout(@RequestParam Map<String, Object> params)
    {
        String msg = params.get("msg").toString();
        template.convertAndSend("FanoutExchange", null, msg);
        return "OK";
    }

    @GetMapping("/header")
    public String sendHeader(@RequestParam Map<String, Object> params) {
        String msg = params.get("msg").toString();
        MessageProperties messageProperties = new MessageProperties();
        if(params.get("token") != null)
        {
            messageProperties.setHeader("token", params.get("token").toString());
        }
        if(params.get("id") != null)
        {
            messageProperties.setHeader("id", params.get("id").toString());
        }
        Message message = new Message(msg.getBytes(), messageProperties);
        template.convertAndSend("HeadersExchange", null, message);
        return "OK";
    }
}
