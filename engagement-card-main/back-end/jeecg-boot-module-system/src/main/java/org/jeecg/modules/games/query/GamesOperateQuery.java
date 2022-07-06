package org.jeecg.modules.games.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GamesOperateQuery {

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "跳过的用户昵称，多选跳过时传入")
    private List<String> usernames;

    @ApiModelProperty(value = "跳过的用户id，多选跳过时传入")
    private List<String> userIds;

    @ApiModelProperty(value = "房间url，后缀")
    private String url;

    @ApiModelProperty(value = "房间id，做同步要用", required = true)
    private String roomId;

    //操作类型：1-卡堆里抽牌，2-复制其他用户的牌，3-从弃卡堆里抽牌，4-弃牌
    @ApiModelProperty(value = "操作类型：1-卡堆里抽牌，2-复制其他用户的牌，3-从弃卡堆里抽牌，4-弃牌，5-暂停游戏，6-跳过用户，7-解除跳过用户，8-游戏结束，9-跳过此次操作,10-替换手牌，11-继续游戏")
    private int operateType;

    @ApiModelProperty(value = "用户抽的牌号，操作类型为2时代表用户复制的牌，操作类型为1时代表用户抽到的牌，操作类型为3时代表用户从弃牌堆里选择的牌")
    private String number;

    @ApiModelProperty(value = "要从牌堆里抽取的手牌列表，操作类型为3时不需要传")
    private List<Integer> noCreatNum;

    @ApiModelProperty(value = "弃牌的牌号，操作类型为4时有效")
    private String foldNumber;

    @ApiModelProperty(value = "自定义字段，后端不处理")
    private String customize;

    @ApiModelProperty(value = "所有用户目前手里的牌，做同步要用", required = true)
    private List<UserCardNumbersQuery> userCardNumbersQueries;

    @ApiModelProperty(value = "弃牌堆里的牌，做同步要用", required = true)
    private List<Integer> foldNumbers;

    @ApiModelProperty(value = "牌堆里的牌，做同步要用", required = true)
    private List<Integer> cardNumbers;

    @ApiModelProperty(value = "游戏时间，做同步要用", required = true)
    private String gameTime;

    @ApiModelProperty(value = "用户操作倒计时，做同步要用", required = true)
    private String countDown;
}
