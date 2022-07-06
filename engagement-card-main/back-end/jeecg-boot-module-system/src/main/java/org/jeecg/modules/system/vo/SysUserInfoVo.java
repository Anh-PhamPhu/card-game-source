package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author qinfeng
 * @Date 2020/1/2 21:58
 * @Description:
 * @Version 1.0
 */
@Data
@ApiModel(value = "个人中心响应数据")
public class SysUserInfoVo {


    /**
     * id
     */
    @ApiModelProperty(value = "id",notes = "id")
    private String id;


    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id",notes = "客户id")
    private String customerId;

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
     * 登录账号
     */
    @Excel(name = "登录账号", width = 15)
    private String username;


    /**
     * md5密码盐
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    /**
     * 头像
     */
    @Excel(name = "头像", width = 15,type = 2)
    private String avatar;


    @ApiModelProperty(value = "年",notes = "年")
    private String year;

    @ApiModelProperty(value = "月",notes = "月")
    private String month;

    @ApiModelProperty(value = "日",notes = "日")
    private String day;



    /**
     * 电话
     */
    @Excel(name = "电话", width = 15)
    private String phone;



    /**
     * 部门code(当前选择登录部门)
     */
    private String orgCode;

    /**部门名称*/
    private transient String orgCodeTxt;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    @Excel(name = "状态", width = 15,dicCode="user_status")
    @Dict(dicCode = "user_status")
    private Integer status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @Excel(name = "删除状态", width = 15,dicCode="del_flag")
    @TableLogic
    private Integer delFlag;

    /**
     * 工号，唯一键
     */
    @Excel(name = "工号", width = 15)
    private String workNo;

    /**
     * 职务，关联职务表
     */
    @Excel(name = "职务", width = 15)
    private String post;

    /**
     * 座机号
     */
    @Excel(name = "座机号", width = 15)
    private String telephone;

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
    /**
     * 同步工作流引擎1同步0不同步
     */
    private Integer activitiSync;

    /**
     * 身份（0 普通成员 1 上级）
     */
    @Excel(name="（1普通成员 2上级）",width = 15)
    private Integer userIdentity;

    /**
     * 负责部门
     */
    @Excel(name="负责部门",width = 15,dictTable ="sys_depart",dicText = "depart_name",dicCode = "id")
    @Dict(dictTable ="sys_depart",dicText = "depart_name",dicCode = "id")
    private String departIds;


    /**
     * 第三方登录的唯一标识
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String thirdId;

    /**
     * 第三方类型 <br>
     * （github/github，wechat_enterprise/企业微信，dingtalk/钉钉）
     */
    private String thirdType;

    /**
     * 多租户id配置，编辑用户的时候设置
     */
    private String relTenantIds;
}
