package org.jeecg.modules.games.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String username;


    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty("当前用户在线状态：online-在线，offline-退出房间")
    private String lineStatus;

    @ApiModelProperty("该房间当前连接人数")
    private int onlineNumber;
}
