package fastwave.biz.publisher.controller;

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
        template.convertAndSend("simpleQueue", msg);
        return "OK";
    }
}
