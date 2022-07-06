package org.jeecg.modules.games.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvitationLogQuery {

    @ApiModelProperty(value = "房间id",required = true)
    private String roomId;

    @ApiModelProperty(value = "被邀请人id",required = true)
    private String userId;
}
