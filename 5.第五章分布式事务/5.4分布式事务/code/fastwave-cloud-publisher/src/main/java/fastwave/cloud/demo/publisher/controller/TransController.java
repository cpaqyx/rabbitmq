package fastwave.cloud.demo.publisher.controller;

import fastwave.cloud.demo.publisher.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("trans")
public class TransController {

    @Autowired
    AccountService accountService;

    @GetMapping("/transfer")
    public String transfer(@RequestParam Map<String, Object> params){
        try {
            accountService.transfer(params);
        }
        catch (Exception e)
        {
            return "转账失败";
        }
        return "转账成功";
    }
}
