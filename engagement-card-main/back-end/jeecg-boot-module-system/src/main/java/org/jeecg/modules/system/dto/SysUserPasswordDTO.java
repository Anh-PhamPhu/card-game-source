package org.jeecg.modules.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户忘记密码传参
 */
@Data
@ApiModel(value = "用户忘记密码传参")
public class SysUserPasswordDTO {

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录邮箱",notes = "登录邮箱")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",notes = "密码")
    private String password;



}
