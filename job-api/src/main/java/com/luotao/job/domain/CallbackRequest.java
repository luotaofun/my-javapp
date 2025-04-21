package com.luotao.job.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname CallbackRequest
 * @Description 当某个事件发生后，e签宝会主动根据贵司传入的回调接收地址（noticeDeveloperUrl ）发送POST请求，推送对应的事件通知信息。
 * @Version 1.css.0.0
 * @Date 2024/12/27 9:47
 * @Author LuoTao
 */
@Data // 自动生成getter、setter及toString方法
@NoArgsConstructor // 自动生成无参构造
@AllArgsConstructor // 自动生成全参构造
public class CallbackRequest {
    /**
     * @Description: 业务事件类型
     **/
    private String action;

    /**
     * @Description: 流程ID
     **/
    private String flowId;

    /**
     * @Description: 账户ID
     **/
    private String accountId;

    /**
     * @Description: 授权账户ID
     **/
    private String authorizedAccountId;

    /**
     * @Description: 签署时间
     **/
    private String signTime;

    /**
     * @Description: 签署顺序
     **/
    private int order;

    /**
     * @Description: 签署结果
     **/
    private int signResult;

    /**
     * @Description: 第三方订单号
     **/
    private String thirdOrderNo;

    /**
     * @Description: 结果描述
     **/
    private String resultDescription;

    /**
     * @Description: 时间戳
     **/
    private long timestamp;

    /**
     * @Description: 第三方用户ID
     **/
    private String thirdPartyUserId;
}
