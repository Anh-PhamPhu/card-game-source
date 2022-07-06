package org.jeecg.modules.games.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.games.query.UserCardNumbersQuery;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SynchronousDataVO {

    @ApiModelProperty(value = "房间id")
    private String roomIdRedis;

    @ApiModelProperty(value = "每个用户的牌")
    private List<UserCardNumbersQuery> userCards;

    @ApiModelProperty(value = "弃牌堆里的牌")
    private List<Integer> foldNumbers;

    @ApiModelProperty(value = "牌堆里的牌")
    private List<Integer> cardNumbers;

    @ApiModelProperty(value = "游戏总时间")
    private String gameTime;

    @ApiModelProperty(value = "用户操作倒计时")
    private String countDown;

    @ApiModelProperty(value = "在线总人数")
    private int onlineCount;

    @ApiModelProperty(value = "自定义数据")
    private String customize;
}
