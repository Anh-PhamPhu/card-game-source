package org.jeecg.modules.games.vo;

import com.stripe.model.ExpandableField;
import com.stripe.model.Price;
import com.stripe.model.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:29
 */
@Data
public class PriceVO {
    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("billing_scheme")
    String billingScheme;
    @ApiModelProperty("created")
    Long created;
    @ApiModelProperty("currency")
    String currency;
    @ApiModelProperty("deleted")
    Boolean deleted;
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("lookup_key")
    String lookupKey;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty("object")
    String object;
    @ApiModelProperty("product")
    ExpandableField<Product> product;
    @ApiModelProperty("recurring")
    Price.Recurring recurring;
    @ApiModelProperty("tiers")
    List<Price.Tier> tiers;
    @ApiModelProperty("tiers_mode")
    String tiersMode;
    @ApiModelProperty("transform_quantity")
    Price.TransformQuantity transformQuantity;
    @ApiModelProperty("type")
    String type;
    @ApiModelProperty("unit_amount")
    Long unitAmount;
    @ApiModelProperty("unit_amount_decimal")
    BigDecimal unitAmountDecimal;
}
