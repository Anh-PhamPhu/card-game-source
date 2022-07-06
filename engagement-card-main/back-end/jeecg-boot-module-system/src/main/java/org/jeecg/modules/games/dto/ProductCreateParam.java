package org.jeecg.modules.games.dto;

import com.stripe.param.ProductCreateParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:30
 */
@Data
@ApiModel(value = "创建商品传参")
public class ProductCreateParam {
    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("attributes")
    List<String> attributes;
    @ApiModelProperty("caption")
    String caption;
    @ApiModelProperty("deactivate_on")
    List<String> deactivateOn;
    @ApiModelProperty("description")
    String description;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("images")
    List<String> images;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty(required = true,value = "商品名")
    @NotNull(message = "商品名不能为空")
    String name;
    @ApiModelProperty("package_dimensions")
    ProductCreateParams.PackageDimensions packageDimensions;
    @ApiModelProperty("shippable")
    Boolean shippable;
    @ApiModelProperty("statement_descriptor")
    String statementDescriptor;
    @ApiModelProperty("type")
    ProductCreateParams.Type type;
    @ApiModelProperty("unit_label")
    String unitLabel;
    @ApiModelProperty("url")
    String url;
}
