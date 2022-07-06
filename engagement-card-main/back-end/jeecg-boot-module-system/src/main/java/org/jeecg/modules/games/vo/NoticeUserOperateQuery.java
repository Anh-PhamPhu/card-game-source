package org.jeecg.modules.games.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeUserOperateQuery {

    @ApiModelProperty(value = "房间id")
    private String roomId;

    @ApiModelProperty(value = "当前操作玩家id")
    private String userId;
}
