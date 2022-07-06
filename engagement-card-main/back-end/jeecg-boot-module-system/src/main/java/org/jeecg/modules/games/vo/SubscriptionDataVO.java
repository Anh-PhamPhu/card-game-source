package org.jeecg.modules.games.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:13
 */
@Data
public class SubscriptionDataVO {

    @ApiModelProperty(value = "订阅id")
    String id;

    @ApiModelProperty(value = "订阅的商品信息")
    ProductListVO productListVO;


    @ApiModelProperty(value = "是否可以自定义URL(0:否，1：是)")
    Integer isCreateUrl;


    @ApiModelProperty(value = "订阅开始时间")
    String startTime;

    @ApiModelProperty(value = "订阅结束时间")
    String endTime;

}
