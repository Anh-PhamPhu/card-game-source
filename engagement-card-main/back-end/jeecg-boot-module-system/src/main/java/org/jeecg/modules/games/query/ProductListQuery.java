package org.jeecg.modules.games.query;

import com.stripe.param.ProductListParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:33
 */
@Data
public class ProductListQuery {

    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("created")
    Object created;
    @ApiModelProperty("ending_before")
    String endingBefore;
    @ApiModelProperty("expand")
    List<String> expand;
    @ApiModelProperty("_stripe_java_extra_param_key")
    Map<String, Object> extraParams;
    @ApiModelProperty("ids")
    List<String> ids;
    @ApiModelProperty("limit")
    Long limit;
    @ApiModelProperty("shippable")
    Boolean shippable;
    @ApiModelProperty("starting_after")
    String startingAfter;
//    @ApiModelProperty("type")
//    ProductListParams.Type type;
    @ApiModelProperty("url")
    String url;
}
