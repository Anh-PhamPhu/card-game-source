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
public class SubscriptionCreateParam {

    @ApiModelProperty("add_invoice_items")
    List<AddInvoiceItem> addInvoiceItems;
    @ApiModelProperty("application_fee_percent")
    BigDecimal applicationFeePercent;
    @ApiModelProperty("backdate_start_date")
    Long backdateStartDate;
    @ApiModelProperty("billing_cycle_anchor")
    Long billingCycleAnchor;
    @ApiModelProperty("billing_thresholds")
    Object billingThresholds;
    @ApiModelProperty("cancel_at")
    Long cancelAt;
    @ApiModelProperty("cancel_at_period_end")
    Boolean cancelAtPeriodEnd;
//    @ApiModelProperty("collection_method")
//    SubscriptionCreateParams.CollectionMethod collectionMethod;
    @ApiModelProperty("coupon")
    String coupon;
    @ApiModelProperty(required = true,value = "客户名")
    @NotNull(message = "客户名不能为空")
    String customer;
    @ApiModelProperty("days_until_due")
    Long daysUntilDue;
    @ApiModelProperty("default_payment_method")
    String defaultPaymentMethod;
    @ApiModelProperty("default_source")
    String defaultSource;
    @ApiModelProperty("default_tax_rates")
    Object defaultTaxRates;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("items")
    List<SubscriptionCreateParams.Item> items;
    @ApiModelProperty("metadata")
    Object metadata;
    @ApiModelProperty("off_session")
    Boolean offSession;
//    @ApiModelProperty("payment_behavior")
//    SubscriptionCreateParams.PaymentBehavior paymentBehavior;
    @ApiModelProperty("pending_invoice_item_interval")
    Object pendingInvoiceItemInterval;
    @ApiModelProperty("prorate")
    Boolean prorate;
//    @ApiModelProperty("proration_behavior")
//    SubscriptionCreateParams.ProrationBehavior prorationBehavior;
    @ApiModelProperty("tax_percent")
    Object taxPercent;
//    @ApiModelProperty("transfer_data")
//    SubscriptionCreateParams.TransferData transferData;
    @ApiModelProperty("trial_end")
    Object trialEnd;
    @ApiModelProperty("trial_from_plan")
    Boolean trialFromPlan;
    @ApiModelProperty("trial_period_days")
    Long trialPeriodDays;
}
