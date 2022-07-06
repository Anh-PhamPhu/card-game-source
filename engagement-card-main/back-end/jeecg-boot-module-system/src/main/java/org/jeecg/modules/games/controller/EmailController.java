package org.jeecg.modules.games.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.games.entity.SendEmail;
import org.jeecg.modules.games.entity.SendEmailCode;
import org.jeecg.modules.games.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: DuplicateCheckAction
 * @Description: 重复校验工具
 * @Author 张代浩
 * @Date 2019-03-25
 * @Version V1.0
 */
@Slf4j
@Api(tags = "发送邮箱")
@RestController
@RequestMapping("/games/email")
public class EmailController {

    @Autowired
    private EmailUtil emailUtil;

    @AutoLog(value = "发送邮箱")
    @ApiOperation(value = "发送邮箱", notes = "发送邮箱")
    @PostMapping("/sendMail")
    public Result<?> sendMail(@RequestBody SendEmail sendEmail) {
        return emailUtil.sendSimpleMail(sendEmail);
    }

    @AutoLog(value = "邮箱验证码")
    @ApiOperation(value = "邮箱验证码", notes = "邮箱验证码")
    @PostMapping("/sendMailCode")
    public Result<?> sendMailCode(@RequestBody SendEmailCode sendEmailCode) {
        return emailUtil.sendMailCode(sendEmailCode);
    }
}
