package org.jeecg.modules.games.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:06
 */
@Data
public class CustomerCreateParam {
   
    @ApiModelProperty("address")
    String address;
    @ApiModelProperty("balance")
    Long balance;
    @ApiModelProperty("coupon")
    String coupon;
    @ApiModelProperty("description")
    String description;
    @ApiModelProperty("email")
    String email;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("invoice_prefix")
    String invoicePrefix;
//    @ApiModelProperty("invoice_settings")
//    com.stripe.param.CustomerCreateParams.InvoiceSettings invoiceSettings;
//    @ApiModelProperty("metadata")
//    Object metadata;
    @ApiModelProperty("name")
    String name;
    @ApiModelProperty("next_invoice_sequence")
    Long nextInvoiceSequence;
    @ApiModelProperty("payment_method")
    String paymentMethod;
    @ApiModelProperty("phone")
    String phone;
    @ApiModelProperty("preferred_locales")
    List<String> preferredLocales;
//    @ApiModelProperty("shipping")
//    Object shipping;
    @ApiModelProperty("source")
    String source;
//    @ApiModelProperty("tax_exempt")
//    ApiRequestParams.EnumParam taxExempt;
//    @ApiModelProperty("tax_id_data")
//    List<com.stripe.param.CustomerCreateParams.TaxIdData> taxIdData;
}
