package com.luotao.job.service.impl;

import com.luotao.job.service.CallbackRequestService;
import org.springframework.stereotype.Service;

/**
 * @Classname CallbackRequestServiceImpl
 * @Description TODO
 * @Version 1.css.0.0
 * @Date 2024/12/27 9:54
 * @Author LuoTao
 */
@Service
public class CallbackRequestServiceImpl implements CallbackRequestService {
    /**
     * @param data
     * @Description: 处理签署完成事件
     * @Author: LuoTao
     * @Date: 2024-12-27 09:53:58
     */
    @Override
    public void handleSignCompleted(Object data) {
        System.out.println("Sign completed: " + data);
    }

    /**
     * @param data
     * @Description: 处理流程结束事件
     * @Author: LuoTao
     * @Date: 2024-12-27 09:53:58
     */
    @Override
    public void handleFlowEnded(Object data) {
        System.out.println("Flow ended: " + data);
    }
}
