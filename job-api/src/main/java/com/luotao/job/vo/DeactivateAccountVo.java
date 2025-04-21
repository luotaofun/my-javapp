package com.luotao.job.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author luotao
 * @description 注销账号请求VO
 */
@Data
public class DeactivateAccountVo {
    /**
     * 密码（验证身份）
     */
    @NotBlank(message = "密码不能为空")
    private String password;
} 