package fastwave.cloud.demo.fastwavebizpublisher.job;

import fastwave.cloud.demo.fastwavebizpublisher.domain.MsgLogDO;
import fastwave.cloud.demo.fastwavebizpublisher.services.MsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class MQMessageJob {
    @Autowired
    private MsgLogService msgLogService;

    @Resource(name = "TemplateReliable")
    private RabbitTemplate reliableTemplate;

    private static Logger logger = LoggerFactory.getLogger(MQMessageJob.class);

    //定时扫描记录表，将发送状态为-1且未超重发次数的消息再次发送，超过重发次数，必要时人工干预，生产环境中可以单独部署定时任务
    @Scheduled(cron ="10/10 * * * * ?" )
    public void scanNoConfirmMsg(){
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("status", -1);
        searchParams.put("tryCount", 3);

        try {
            List<MsgLogDO> list = msgLogService.list(searchParams);
            for(MsgLogDO item : list)
            {
                item.setTryCount(item.getTryCount() + 1);
                msgLogService.update(item);
                reliableTemplate.convertAndSend(item.getExchange(), item.getRoutingKey(), item.getMsg(), new CorrelationData(item.getMsgId()));
            }
        }
        catch(Exception e)
        {
            logger.error("扫描作业出错，信息：" + e.getMessage());
        }
    }
}