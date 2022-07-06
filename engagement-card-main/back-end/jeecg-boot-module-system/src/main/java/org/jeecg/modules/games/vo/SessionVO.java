package org.jeecg.modules.games.vo;

import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "创建session返回值")
public class SessionVO {
    @ApiModelProperty("allow_promotion_codes")
    Boolean allowPromotionCodes;
    @ApiModelProperty("amount_subtotal")
    Long amountSubtotal;
    @ApiModelProperty("amount_total")
    Long amountTotal;
//    @ApiModelProperty("automatic_tax")
//    Session.AutomaticTax automaticTax;
    @ApiModelProperty("billing_address_collection")
    String billingAddressCollection;
    @ApiModelProperty("cancel_url")
    String cancelUrl;
    @ApiModelProperty("client_reference_id")
    String clientReferenceId;
    @ApiModelProperty("currency")
    String currency;
//    @ApiModelProperty("customer")
//    ExpandableField<Customer> customer;
//    @ApiModelProperty("customer_details")
//    Session.CustomerDetails customerDetails;
    @ApiModelProperty("customer_email")
    String customerEmail;
    @ApiModelProperty("id")
    String id;
//    @ApiModelProperty("line_items")
//    LineItemCollection lineItems;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("locale")
    String locale;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("mode")
    String mode;
    @ApiModelProperty("object")
    String object;
//    @ApiModelProperty("payment_intent")
//    ExpandableField<PaymentIntent> paymentIntent;
//    @ApiModelProperty("payment_method_options")
//    Session.PaymentMethodOptions paymentMethodOptions;
    @ApiModelProperty("payment_method_types")
    List<String> paymentMethodTypes;
    @ApiModelProperty("payment_status")
    String paymentStatus;
//    @ApiModelProperty("setup_intent")
//    ExpandableField<SetupIntent> setupIntent;
//    @ApiModelProperty("shipping")
//    ShippingDetails shipping;
//    @ApiModelProperty("shipping_address_collection")
//    Session.ShippingAddressCollection shippingAddressCollection;
    @ApiModelProperty("submit_type")
    String submitType;
//    @ApiModelProperty("subscription")
//    ExpandableField<Subscription> subscription;
    @ApiModelProperty("success_url")
    String successUrl;
//    @ApiModelProperty("tax_id_collection")
//    Session.TaxIDCollection taxIdCollection;
//    @ApiModelProperty("total_details")
//    Session.TotalDetails totalDetails;
    @ApiModelProperty("url")
    String url;
}
