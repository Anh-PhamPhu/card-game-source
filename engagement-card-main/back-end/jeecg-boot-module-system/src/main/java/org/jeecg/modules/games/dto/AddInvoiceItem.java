package org.jeecg.modules.games.dto;

import com.stripe.param.SubscriptionCreateParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:10
 */
@Data
public class AddInvoiceItem {
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("price")
    String price;
    @ApiModelProperty("price_data")
    PriceData priceData;
    @ApiModelProperty("quantity")
    Long quantity;
}
