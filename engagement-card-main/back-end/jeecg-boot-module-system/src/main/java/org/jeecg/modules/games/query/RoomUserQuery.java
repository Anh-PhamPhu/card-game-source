package org.jeecg.modules.games.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoomUserQuery implements Comparable<RoomUserQuery> {

    @TableId(type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "房间id", required = true)
    private String roomId;

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "游戏顺序", required = true)
    private int sequence;
    
    @ApiModelProperty(value = "用户昵称")
    private String username;
    
    @ApiModelProperty(value = "邮箱")
    private String email;


    @Override
    public int compareTo(RoomUserQuery o) {
        return this.sequence - o.sequence;
    }
}
