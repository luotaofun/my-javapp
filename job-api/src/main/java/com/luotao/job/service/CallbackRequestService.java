package com.luotao.job.service;

import com.luotao.job.domain.CallbackRequest;

public interface CallbackRequestService {
    /**
    * @Description: 处理签署完成事件
    * @Author: LuoTao
    * @Date: 2024-12-27 09:53:58
    **/
    void handleSignCompleted(Object data);
    /**
     * @Description: 处理流程结束事件
     * @Author: LuoTao
     * @Date: 2024-12-27 09:53:58
     **/
    void handleFlowEnded(Object data);

}
