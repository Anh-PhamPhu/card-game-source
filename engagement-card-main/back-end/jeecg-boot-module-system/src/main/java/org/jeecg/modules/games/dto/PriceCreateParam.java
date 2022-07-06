package org.jeecg.modules.games.dto;

import com.stripe.net.ApiRequestParams;
import com.stripe.param.PriceCreateParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:27
 */
@Data
public class PriceCreateParam {
    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("billing_scheme")
    PriceCreateParams.BillingScheme billingScheme;
    @ApiModelProperty(value = "币种",required = true)
    @NotNull(message = "币种不能为空")
    String currency;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("lookup_key")
    String lookupKey;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty(value = "商品id",required = true)
    @NotNull(message = "商品id不能为空")
    String product;
    @ApiModelProperty("product_data")
    PriceCreateParams.ProductData productData;
    @ApiModelProperty("tiers")
    List<PriceCreateParams.Tier> tiers;
    @ApiModelProperty("tiers_mode")
    PriceCreateParams.TiersMode tiersMode;
    @ApiModelProperty("transfer_lookup_key")
    Boolean transferLookupKey;
    @ApiModelProperty("transform_quantity")
    PriceCreateParams.TransformQuantity transformQuantity;
    @ApiModelProperty(value = "金额",required = true)
    @NotNull(message = "金额不能为空")
    Long unitAmount;
    @ApiModelProperty("unit_amount_decimal")
    BigDecimal unitAmountDecimal;
    @ApiModelProperty(value = "时间间隔",required = true)
    @NotNull(message = "时间间隔不能为空")
    String interval;

}
