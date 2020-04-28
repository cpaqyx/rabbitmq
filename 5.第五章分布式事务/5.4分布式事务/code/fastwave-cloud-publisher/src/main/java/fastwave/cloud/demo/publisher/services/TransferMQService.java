package fastwave.cloud.demo.publisher.services;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.demo.publisher.config.TransConfig;
import fastwave.cloud.demo.publisher.domain.MsgLogDO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class TransferMQService {

    @Resource(name = "TemplateTrans")
    private RabbitTemplate template;

    @Autowired
    MsgLogService msgLogService;

    @PostConstruct
    private void initRabbitTemplate(){
        //设置消息发送确认回调，发送成功后更新消息表状态
        template.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if(ack){
                MsgLogDO msgLogDO = new MsgLogDO();
                msgLogDO.setMsgId(correlationData.getId());
                msgLogDO.setStatus(1);
                msgLogService.update(msgLogDO);
            }
        });

        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                Date curTime = new Date();
                MsgLogDO msgLogDO = new MsgLogDO();
                msgLogDO.setMsgId(UUID.randomUUID().toString());
                msgLogDO.setMsg(message.toString());
                msgLogDO.setExchange(exchange);
                msgLogDO.setRoutingKey(routingKey);
                msgLogDO.setStatus(4);
                msgLogDO.setTryCount(0);
                msgLogDO.setCreateTime(curTime);
                msgLogDO.setUpdateTime(curTime);
                msgLogDO.setCreateTime(curTime);
                msgLogService.save(msgLogDO);
            }
        });
    }

    public void sendMessage(Map<String, Object> params){
        String msg = JSON.toJSONString(params);
        String transId = params.get("transId").toString();
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setMessageId(transId)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();

        template.convertAndSend(TransConfig.TRANSFER_EXCHANGE_NAME,TransConfig.TRANSFER_ROUTING_KEY, message,
                new CorrelationData(transId));
    }
}