package org.jeecg.modules.games.vo;

import com.stripe.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 18:13
 */
@Data
public class SubscriptionVO {
    @ApiModelProperty("application_fee_percent")
    BigDecimal applicationFeePercent;
    @ApiModelProperty("billing_cycle_anchor")
    Long billingCycleAnchor;
//    @ApiModelProperty("billing_thresholds")
//    Subscription.BillingThresholds billingThresholds;
    @ApiModelProperty("cancel_at")
    Long cancelAt;
    @ApiModelProperty("cancel_at_period_end")
    Boolean cancelAtPeriodEnd;
    @ApiModelProperty("canceled_at")
    Long canceledAt;
    @ApiModelProperty("collection_method")
    String collectionMethod;
    @ApiModelProperty("created")
    Long created;
    @ApiModelProperty("current_period_end")
    Long currentPeriodEnd;
    @ApiModelProperty("current_period_start")
    Long currentPeriodStart;
//    @ApiModelProperty("customer")
//    ExpandableField<Customer> customer;
    @ApiModelProperty("days_until_due")
    Long daysUntilDue;
//    @ApiModelProperty("default_payment_method")
//    ExpandableField<PaymentMethod> defaultPaymentMethod;
//    @ApiModelProperty("default_source")
//    ExpandableField<PaymentSource> defaultSource;
//    @ApiModelProperty("default_tax_rates")
//    List<TaxRate> defaultTaxRates;
//    @ApiModelProperty("discount")
//    Discount discount;
    @ApiModelProperty("ended_at")
    Long endedAt;
    @ApiModelProperty("id")
    String id;
//    @ApiModelProperty("items")
//    SubscriptionItemCollection items;
//    @ApiModelProperty("latest_invoice")
//    ExpandableField<Invoice> latestInvoice;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("next_pending_invoice_item_invoice")
    Long nextPendingInvoiceItemInvoice;
    @ApiModelProperty("object")
    String object;
//    @ApiModelProperty("pause_collection")
//    Subscription.PauseCollection pauseCollection;
//    @ApiModelProperty("pending_invoice_item_interval")
//    Subscription.PendingInvoiceItemInterval pendingInvoiceItemInterval;
//    @ApiModelProperty("pending_setup_intent")
//    ExpandableField<SetupIntent> pendingSetupIntent;
//    @ApiModelProperty("pending_update")
//    Subscription.PendingUpdate pendingUpdate;
//    @ApiModelProperty("plan")
//    Plan plan;
    @ApiModelProperty("quantity")
    Long quantity;
//    @ApiModelProperty("schedule")
//    ExpandableField<SubscriptionSchedule> schedule;
    @ApiModelProperty("start_date")
    Long startDate;
    @ApiModelProperty("status")
    String status;
    @ApiModelProperty("tax_percent")
    BigDecimal taxPercent;
//    @ApiModelProperty("transfer_data")
//    Subscription.TransferData transferData;
    @ApiModelProperty("trial_end")
    Long trialEnd;
    @ApiModelProperty("trial_start")
    Long trialStart;
}
