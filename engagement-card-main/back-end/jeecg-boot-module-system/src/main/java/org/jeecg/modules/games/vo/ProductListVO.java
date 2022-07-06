package org.jeecg.modules.games.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 神州
 * @date 2022年04月25日 17:26
 */
@Data
@ApiModel(value = "商品列表返回值")
public class ProductListVO {

    @ApiModelProperty(value = "商品id")
    String id;
    @ApiModelProperty(value = "商品价格")
    String price;
    @ApiModelProperty(value = "商品价格数")
    Integer priceCount;
    @ApiModelProperty(value = "单位")
    String company;
    @ApiModelProperty(value = "时间跨度")
    String time;
    @ApiModelProperty(value = "商品权限")
    List<String> jurisdiction;
    @ApiModelProperty(value = "商品名")
    String name;
    @ApiModelProperty(value = "商品描述")
    String describe;

}
