package fastwave.biz.publisher.controller;

import fastwave.lib.common.config.SimpleConst;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/amqp")
public class AmqpController {

    @Autowired
    AmqpTemplate template;

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
}
