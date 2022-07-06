package org.jeecg.modules.games.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:11
 */
@Data
public class PriceData {
    @ApiModelProperty("currency")
    String currency;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("product")
    String product;
    @ApiModelProperty("unit_amount")
    Long unitAmount;
    @ApiModelProperty("unit_amount_decimal")
    BigDecimal unitAmountDecimal;
}
