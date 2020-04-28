package fastwave.cloud.demo.fastwavebizpublisher.config;

import fastwave.cloud.demo.fastwavebizpublisher.domain.MsgLogDO;
import fastwave.cloud.demo.fastwavebizpublisher.services.MsgLogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.UUID;

@Configuration
public class TemplateReliableConfig {
    @Autowired
    MsgLogService msgLogService;

//    @Scope("prototype")
    @Bean(name = "TemplateReliable")
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());

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
        return template;
    }
}
