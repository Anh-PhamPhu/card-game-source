package org.jeecg.modules.games.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:10
 */
@Data
@ApiModel("上传结果参数")
public class AddResultParam {
    @ApiModelProperty("用户手中牌")
    String userCard;
    @ApiModelProperty("房间id")
    String roomId;
    @ApiModelProperty("用户名")
    String userName;
    @ApiModelProperty("用户角色（0房主1参与者）")
    Integer userRole;
}
