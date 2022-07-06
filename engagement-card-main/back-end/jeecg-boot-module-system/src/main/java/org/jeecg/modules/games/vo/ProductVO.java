package org.jeecg.modules.games.vo;

import com.stripe.model.PackageDimensions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 神州
 * @date 2022年04月25日 16:31
 */
@Data
public class ProductVO {
    @ApiModelProperty("active")
    Boolean active;
    @ApiModelProperty("attributes")
    List<String> attributes;
    @ApiModelProperty("caption")
    String caption;
    @ApiModelProperty("created")
    Long created;
    @ApiModelProperty("deactivate_on")
    List<String> deactivateOn;
    @ApiModelProperty("deleted")
    Boolean deleted;
    @ApiModelProperty("description")
    String description;
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("images")
    List<String> images;
    @ApiModelProperty("livemode")
    Boolean livemode;
    @ApiModelProperty("metadata")
    Map<String, String> metadata;
    @ApiModelProperty("name")
    String name;
    @ApiModelProperty("object")
    String object;
    @ApiModelProperty("package_dimensions")
    PackageDimensions packageDimensions;
    @ApiModelProperty("shippable")
    Boolean shippable;
    @ApiModelProperty("statement_descriptor")
    String statementDescriptor;
    @ApiModelProperty("type")
    String type;
    @ApiModelProperty("unit_label")
    String unitLabel;
    @ApiModelProperty("updated")
    Long updated;
    @ApiModelProperty("url")
    String url;
}
