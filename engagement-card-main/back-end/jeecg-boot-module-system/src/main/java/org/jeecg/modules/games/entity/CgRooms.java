package org.jeecg.modules.games.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CgRooms {

    @TableId(type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "房间名称", required = true)
    private String roomName;

    @ApiModelProperty(value = "开始时间,时间戳", required = true)
    private String startTime;

    @ApiModelProperty(value = "主题", required = true)
    private String theme;

    @ApiModelProperty(value = "是否可复制：1-是，0-否", required = true)
    private int isCopy;

    @ApiModelProperty(value = "是否公开：1-是，0-否", required = true)
    private int isPublic;

    @ApiModelProperty(value = "人员数量", required = true)
    private int peopleNumber;

    @ApiModelProperty(value = "路径", required = false)
    private String url;

    @ApiModelProperty(value = "每回合时间", required = true)
    private String turnTime;

    @ApiModelProperty(value = "路径类型：1-随机，2-固定", required = true)
    private int isCustom;

    @ApiModelProperty(value = "房主的用户id", required = true)
    private String homeowner;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "游戏状态：1-等待，2-游戏中，3-已结束", required = false)
    private int status;
}
