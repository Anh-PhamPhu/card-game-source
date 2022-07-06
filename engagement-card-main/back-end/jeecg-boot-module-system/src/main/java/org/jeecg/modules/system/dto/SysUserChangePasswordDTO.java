package org.jeecg.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户变更密码传参
 */
@Data
@ApiModel(value = "用户变更密码传参")
public class SysUserChangePasswordDTO {

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码",notes = "旧密码")
    private String oldpassword;

    /**
     * 密码
     */
    @ApiModelProperty(value = "新密码",notes = "新密码")
    private String password;

    /**
     * 密码
     */
    @ApiModelProperty(value = "确认新密码",notes = "确认新密码")
    private String confirmpassword;




}
