package org.jeecg.modules.games.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendEmail {

    @ApiModelProperty(value = "类型", required = true)
    private String subject;

    private String to = "online-engagementcard@triplevalue.jp";

    @ApiModelProperty(value = "内容", required = true)
    private String content;
}
