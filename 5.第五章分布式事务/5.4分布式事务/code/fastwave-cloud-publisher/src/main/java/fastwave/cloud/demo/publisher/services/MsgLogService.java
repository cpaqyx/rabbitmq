package fastwave.cloud.demo.publisher.services;


import fastwave.cloud.demo.publisher.domain.MsgLogDO;

import java.util.List;
import java.util.Map;


public interface MsgLogService {

    List<MsgLogDO> list(Map<String, Object> map);

    int save(MsgLogDO msgLogDO);

    int update(MsgLogDO msgLogDO);

    int increaseTryTimes(MsgLogDO msgLogDO);
}
