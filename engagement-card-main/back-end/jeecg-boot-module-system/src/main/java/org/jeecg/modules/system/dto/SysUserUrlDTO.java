package org.jeecg.modules.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户注册参数
 */
@Data
@ApiModel(value = "用户设定固定url传参")
public class SysUserUrlDTO {


    /**
     * 订阅的商品名
     */
    @NotBlank(message = "订阅的商品名不能为空")
    @ApiModelProperty(value = "订阅的商品名",notes = "订阅的商品名")
    private String proName;

    /**
     * 用户url List
     */
    @NotBlank(message = "用户url不能为空")
    @ApiModelProperty(value = "用户url[逗号作为分隔符]",notes = "用户url[逗号作为分隔符]")
    private String userUrl;




}
