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
public class CgResult {

    @TableId(type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "房间Id", required = true)
    private String roomId;

    @ApiModelProperty(value = "用户名", required = false)
    private String userName;

    @ApiModelProperty(value = "用户手中牌", required = false)
    private String userCard;

    @ApiModelProperty(value = "用户角色", required = false)
    private int userRole;

}
