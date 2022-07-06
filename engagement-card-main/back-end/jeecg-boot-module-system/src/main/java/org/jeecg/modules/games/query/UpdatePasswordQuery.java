package org.jeecg.modules.games.query;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePasswordQuery {

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;

    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
