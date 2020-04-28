package fastwave.cloud.demo.subscriber.controller;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import fastwave.cloud.demo.subscriber.common.CacheUtil;
import fastwave.cloud.demo.subscriber.config.TransConfig;
import fastwave.cloud.demo.subscriber.services.AccountService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class TransReceiver {

    @Autowired
    CacheUtil cacheUtil;

    @Autowired
    AccountService accountService;


    @RabbitListener(queues = TransConfig.TRANSFER_QUEUE_NAME)
    public void HandlerMessage(Message message) throws IOException {
        String messageId = message.getMessageProperties().getMessageId();
        if(messageId != null && !cacheUtil.exists(messageId))
        {
            String msg = new String(message.getBody());
            Map<String, Object>  map = JSONObject.parseObject(msg, Map.class);
            Integer id2 = Integer.parseInt(map.get("id2").toString());
            Float amount = Float.parseFloat(map.get("amount").toString());
            accountService.transfer(id2, amount);
            cacheUtil.set(messageId, true);
        }
        else {
            System.out.println("已消费，不需要重复消费");
        }
    }
}

//    @RabbitListener(queues = TransConfig.TRANSFER_QUEUE_NAME)
//    public void HandlerMessage(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long tag,
//                               @Header(AmqpHeaders.REDELIVERED) boolean reDelivered ) throws IOException {
//        try {
//            String messageId = message.getMessageProperties().getMessageId();
//            if(messageId != null && !cacheUtil.exists(messageId))
//            {
//                String msg = new String(message.getBody());
//                Map<String, Object>  map = JSONObject.parseObject(msg, Map.class);
//                Integer id2 = Integer.parseInt(map.get("id2").toString());
//                Float amount = Float.parseFloat(map.get("amount").toString());
//                accountService.transfer(id2, amount);
//                channel.basicAck(tag,false);
//            }
//        } catch (Exception e) {
//            if(reDelivered){
//                System.out.println("消息已重复处理失败：" + message);
//                channel.basicReject(tag,false);
//            }else{
//                System.out.println("消息处理失败");
//                //重新入队一次
//                channel.basicNack(tag,false,true);
//            }
//        }
//    }
//}
