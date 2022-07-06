package org.jeecg.modules.games.vo;

import com.stripe.model.ExpandableField;
import com.stripe.model.Plan;
import com.stripe.model.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 17:19
 */
@Data
public class PlanVO {

    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("aggregate_usage")
    String aggregateUsage;
    @ApiModelProperty("amount")
    Long amount;
    @ApiModelProperty("amount_decimal")
    BigDecimal amountDecimal;
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
    @ApiModelProperty("interval")
    String interval;
    @ApiModelProperty("interval_count")
    Long intervalCount;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty("object")
    String object;
    @ApiModelProperty("product")
    ExpandableField<Product> product;
    @ApiModelProperty("tiers")
    List<Plan.Tier> tiers;
    @ApiModelProperty("tiers_mode")
    String tiersMode;
    @ApiModelProperty("transform_usage")
    Plan.TransformUsage transformUsage;
    @ApiModelProperty("trial_period_days")
    Long trialPeriodDays;
    @ApiModelProperty("usage_type")
    String usageType;
}
