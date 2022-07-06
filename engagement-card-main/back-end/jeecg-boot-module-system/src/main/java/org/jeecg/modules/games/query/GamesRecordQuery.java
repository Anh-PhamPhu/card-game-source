package org.jeecg.modules.games.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GamesRecordQuery {

    @ApiModelProperty(value = "第几页",required = true)
    private int pageNo;

    @ApiModelProperty(value = "每页多少数据",required = true)
    private int pageSize;

    @ApiModelProperty(value = "用户id",required = true)
    private String userId;
}
