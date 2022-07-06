package org.jeecg.modules.games.dto;

import com.stripe.param.PlanCreateParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 17:15
 */
@ApiModel(value = "创建plan传参")
@Data
public class PlanCreateParam {

    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("aggregate_usage")
    PlanCreateParams.AggregateUsage aggregateUsage;
    @ApiModelProperty(value = "金额",required = true)
    @NotNull(message = "金额不能为空")
    Long amount;
    @ApiModelProperty("amount_decimal")
    BigDecimal amountDecimal;
    @ApiModelProperty("billing_scheme")
    PlanCreateParams.BillingScheme billingScheme;
    @ApiModelProperty(value = "币种",required = true)
    @NotNull(message = "币种不能为空")
    String currency;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty(value = "时间间隔",required = true)
    @NotNull(message = "时间间隔不能为空")
    String interval;
    @ApiModelProperty("interval_count")
    Long intervalCount;
    @ApiModelProperty("metadata")
    Object metadata;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty(value = "商品id",required = true)
    @NotNull(message = "商品id不能为空")
    Object product;
    @ApiModelProperty("tiers")
    List<PlanCreateParams.Tier> tiers;
    @ApiModelProperty("tiers_mode")
    PlanCreateParams.TiersMode tiersMode;
    @ApiModelProperty("transform_usage")
    PlanCreateParams.TransformUsage transformUsage;
    @ApiModelProperty("trial_period_days")
    Long trialPeriodDays;
    @ApiModelProperty("usage_type")
    PlanCreateParams.UsageType usageType;
}
