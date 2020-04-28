package fastwave.cloud.demo.fastwavebizpublisher.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.demo.fastwavebizpublisher.domain.MsgLogDO;
import fastwave.cloud.demo.fastwavebizpublisher.services.MsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("amqp")
public class AmqpController {

    @Autowired
    RabbitTemplate template;

    @Autowired
    MsgLogService msgLogService;

    @Resource(name = "TemplateReliable")
    private RabbitTemplate templateReliable;

    private static Logger logger = LoggerFactory.getLogger(AmqpController.class);

    @GetMapping("/reliable")
    public String reliable(@RequestParam Map<String, Object> params)
    {
        try
        {
            String msg = JSON.toJSONString(params);
            String uuid = UUID.randomUUID().toString();
            Date curTime = new Date();

            MsgLogDO msgLogDO = new MsgLogDO();
            msgLogDO.setMsgId(uuid);
            msgLogDO.setMsg(msg);
            msgLogDO.setExchange("EmailExchange");
            msgLogDO.setRoutingKey("EmailRouting");
            msgLogDO.setStatus(-1);
            msgLogDO.setTryCount(0);
            msgLogDO.setCreateTime(curTime);
            msgLogDO.setUpdateTime(curTime);
            msgLogDO.setCreateTime(curTime);
            msgLogService.save(msgLogDO);

            templateReliable.convertAndSend("EmailExchange", "EmailRouting", msg, new CorrelationData(uuid));
            return "已成功发送到broker";
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return "出现异常，请稍后重试";
        }
    }

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
