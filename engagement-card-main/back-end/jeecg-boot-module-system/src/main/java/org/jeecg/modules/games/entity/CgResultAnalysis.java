package org.jeecg.modules.games.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CgResultAnalysis {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "A", required = true)
    private Integer A;

    @ApiModelProperty(value = "B", required = true)
    private Integer B;

    @ApiModelProperty(value = "C", required = true)
    private Integer C;

    @ApiModelProperty(value = "D", required = true)
    private Integer D;

    @ApiModelProperty(value = "E", required = true)
    private Integer E;

    @ApiModelProperty(value = "F", required = true)
    private Integer F;

    @ApiModelProperty(value = "G", required = true)
    private Integer G;

    @ApiModelProperty(value = "H", required = true)
    private Integer H;

    @ApiModelProperty(value = "I", required = true)
    private Integer I;

}
