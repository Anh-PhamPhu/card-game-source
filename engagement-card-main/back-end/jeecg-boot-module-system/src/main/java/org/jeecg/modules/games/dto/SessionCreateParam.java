package org.jeecg.modules.games.dto;

import com.stripe.param.SubscriptionCreateParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:04
 */
@Data
public class SessionCreateParam {

    @NotNull(message = "价格id不能为空")
    @ApiModelProperty(value = "price",required = true)
    String price;


    @NotNull(message = "客户id不能为空")
    @ApiModelProperty(value = "price",required = true)
    String customerId;

}
