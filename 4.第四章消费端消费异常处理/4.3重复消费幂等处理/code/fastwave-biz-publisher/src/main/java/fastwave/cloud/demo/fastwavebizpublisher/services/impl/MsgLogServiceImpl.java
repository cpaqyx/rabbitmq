package fastwave.cloud.demo.fastwavebizpublisher.services.impl;

import fastwave.cloud.demo.fastwavebizpublisher.dao.MsgLogDao;
import fastwave.cloud.demo.fastwavebizpublisher.domain.MsgLogDO;
import fastwave.cloud.demo.fastwavebizpublisher.services.MsgLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MsgLogServiceImpl implements MsgLogService {

    @Resource
    private MsgLogDao msgLogDao;


    @Override
    public List<MsgLogDO> list(Map<String, Object> map) {
        return msgLogDao.list(map);
    }

    @Override
    public int save(MsgLogDO msgLogDO) {
        return msgLogDao.save(msgLogDO);
    }

    @Override
    public int update(MsgLogDO msgLogDO) {
        return msgLogDao.update(msgLogDO);
    }

    @Override
    public int increaseTryTimes(MsgLogDO msgLogDO) {
        return msgLogDao.increaseTryTimes();
    }
}
