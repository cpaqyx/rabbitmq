package fastwave.cloud.demo.fastwavebizsubscriber.services;

import com.alibaba.fastjson.JSONObject;
import fastwave.cloud.demo.fastwavebizsubscriber.common.CacheUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailReceiver {
    @Autowired
    CacheUtil cacheUtil;

//    @RabbitListener(queues = "EmailQueue")
//    public void receiver(String msg) throws Exception {
//
//
//        System.out.println("收到的消息是：" + msg);
////        throw new Exception("消费消息出现异常");
//    }

    @RabbitListener(queues = "EmailQueue")
    public void receiver(Message message) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        if(messageId != null && !cacheUtil.exists(messageId))
        {
            String msg = new String(message.getBody());
            Map<String, Object>  map = JSONObject.parseObject(msg, Map.class);
            System.out.println("开始开送邮件：" + msg);
            cacheUtil.set(messageId, true);
        }else
        {
            System.out.println("已经消费过了");
        }
        throw new Exception("消费消息出现异常");
    }

}
