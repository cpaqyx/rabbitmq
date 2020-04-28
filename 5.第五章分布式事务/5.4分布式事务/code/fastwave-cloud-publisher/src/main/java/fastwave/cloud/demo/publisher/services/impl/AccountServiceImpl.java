package fastwave.cloud.demo.publisher.services.impl;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.demo.publisher.config.TransConfig;
import fastwave.cloud.demo.publisher.dao.AccountDao;
import fastwave.cloud.demo.publisher.dao.MsgLogDao;
import fastwave.cloud.demo.publisher.dao.TransDao;
import fastwave.cloud.demo.publisher.domain.AccountDO;
import fastwave.cloud.demo.publisher.domain.MsgLogDO;
import fastwave.cloud.demo.publisher.domain.TransDO;
import fastwave.cloud.demo.publisher.services.AccountService;
import fastwave.cloud.demo.publisher.services.TransferMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Resource
    private TransDao transDao;

    @Autowired
    private TransferMQService rabbitMQService;

    @Resource
    MsgLogDao msgLogDao;

    @Override
    public AccountDO get(Integer id){
        return accountDao.get(id);
    }

    @Override
    public List<AccountDO> list(Map<String, Object> map){
        return accountDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map){
        return accountDao.count(map);
    }

    @Override
    public int save(AccountDO account){
        return accountDao.save(account);
    }

    @Override
    public int update(AccountDO account){
        return accountDao.update(account);
    }

    @Override
    public int remove(Integer id){
        return accountDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids){
        return accountDao.batchRemove(ids);
    }

    @Override
    @Transactional
    public String transfer(Integer id1, Integer id2, float amount)
    {
//        try {
            //扣减
            AccountDO user1 = accountDao.get(id1);
            user1.setBalance(user1.getBalance() - amount);
            accountDao.update(user1);

            //增加
            AccountDO user2 = accountDao.get(id2);
            user2.setBalance(user2.getBalance() + amount);
            accountDao.update(user2);

            //流水日志
            TransDO transDO = new TransDO();
            transDO.setId(UUID.randomUUID().toString());
            transDO.setStatus(1);
            transDao.save(transDO);

            return "转账成功";
//        }catch (Exception e)
//        {
//            return "转账失败";
//        }
    }

    @Override
    @Transactional
    public void transfer(Integer id1, Float amount, String transId) {

        AccountDO user1 = accountDao.get(id1);
        user1.setBalance(user1.getBalance() - amount);
        accountDao.update(user1);

        TransDO tranDO = new TransDO();
        tranDO.setId(transId);
        tranDO.setStatus(1);
        transDao.save(tranDO);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String transfer(Map<String, Object> params) {

        Integer id1 = Integer.parseInt(params.get("id1").toString());
        Integer id2 = Integer.parseInt(params.get("id2").toString());
        Float amount = Float.parseFloat(params.get("amount").toString());
        String transId = UUID.randomUUID().toString();
        params.put("transId", transId);


        // 1.扣款操作
        AccountDO user1 = accountDao.get(id1);
        user1.setBalance(user1.getBalance() - amount);
        accountDao.update(user1);

        // 2.扣款日志
        TransDO transDO = new TransDO();
        transDO.setId(transId);
        String message = JSON.toJSONString(params);
        transDO.setMessage(message);
        transDao.save(transDO);

        // 3.保存事务消息,防止发送失败后,可以在作业中重试,另外还可以手工处理
        String msg = JSON.toJSONString(params);
        Date curTime = new Date();
        MsgLogDO msgLogDO = new MsgLogDO();
        msgLogDO.setMsgId(transId);
        msgLogDO.setMsg(msg);
        msgLogDO.setExchange(TransConfig.TRANSFER_EXCHANGE_NAME);
        msgLogDO.setRoutingKey(TransConfig.TRANSFER_ROUTING_KEY);
        msgLogDO.setStatus(-1);
        msgLogDO.setTryCount(0);
        msgLogDO.setCreateTime(curTime);
        msgLogDO.setUpdateTime(curTime);
        msgLogDO.setCreateTime(curTime);
        msgLogDao.save(msgLogDO);

        //4.发送消息
        rabbitMQService.sendMessage(params);

        return "转账成功";
    }
}
