package org.jeecg.modules.games.vo;

import com.stripe.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:12
 */
@Data
public class CustomerVO {
//    @ApiModelProperty("address")
//    Address address;
    @ApiModelProperty("balance")
    Long balance;
    @ApiModelProperty("created")
    Long created;
    @ApiModelProperty("currency")
    String currency;
//    @ApiModelProperty("default_source")
//    ExpandableField<PaymentSource> defaultSource;
    @ApiModelProperty("deleted")
    Boolean deleted;
    @ApiModelProperty("delinquent")
    Boolean delinquent;
    @ApiModelProperty("description")
    String description;
//    @ApiModelProperty("discount")
//    Discount discount;
    @ApiModelProperty("email")
    String email;
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("invoice_prefix")
    String invoicePrefix;
//    @ApiModelProperty("invoice_settings")
//    Customer.InvoiceSettings invoiceSettings;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("name")
    String name;
    @ApiModelProperty("next_invoice_sequence")
    Long nextInvoiceSequence;
    @ApiModelProperty("object")
    String object;
    @ApiModelProperty("phone")
    String phone;
    @ApiModelProperty("preferred_locales")
    List<String> preferredLocales;
//    @ApiModelProperty("shipping")
//    ShippingDetails shipping;
//    @ApiModelProperty("sources")
//    PaymentSourceCollection sources;
//    @ApiModelProperty("subscriptions")
//    SubscriptionCollection subscriptions;
    @ApiModelProperty("tax_exempt")
    String taxExempt;
//    @ApiModelProperty("tax_ids")
//    TaxIdCollection taxIds;
}
