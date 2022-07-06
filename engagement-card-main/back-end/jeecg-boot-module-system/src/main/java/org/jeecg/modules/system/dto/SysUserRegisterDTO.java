package org.jeecg.modules.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册参数
 */
@Data
@ApiModel(value = "用户注册传参")
public class SysUserRegisterDTO {


    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名",notes = "真实姓名")
    private String realname;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",notes = "昵称")
    private String nickname;

    /**
     * 读音
     */
    @ApiModelProperty(value = "读音",notes = "读音")
    private String falsename;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",notes = "密码")
    private String password;



    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日",notes = "生日")
    private Date birthday;

    /**
     * 性别（0：秘密 1：男 2：女）
     */
    @ApiModelProperty(value = "性别（0：秘密 1：男 2：女）",notes = "性别（0：秘密 1：男 2：女）")
    @Dict(dicCode = "sex")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "邮箱",notes = "邮箱")
    private String email;


    /**
     * 地址
     */
    @ApiModelProperty(value = "住址",notes = "住址")
    private String address;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

}
