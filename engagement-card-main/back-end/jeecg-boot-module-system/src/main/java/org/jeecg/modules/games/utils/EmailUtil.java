package org.jeecg.modules.games.utils;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.games.entity.SendEmail;
import org.jeecg.modules.games.entity.SendEmailCode;
import org.jeecg.modules.games.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;

@Service
@Slf4j
public class EmailUtil implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
    @Autowired
    private JavaMailSender mailSender;

    // 配置文件中我的谷歌邮箱
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public Result<?> sendSimpleMail(SendEmail sendEmail) {
        Result<?> result = new Result<>();
        try {
            //创建SimpleMailMessage对象
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件发送人
            message.setFrom(from);
            //邮件接收人
            message.setTo(sendEmail.getTo());
            //邮件主题
            message.setSubject(sendEmail.getSubject());
            //邮件内容
            message.setText(sendEmail.getContent());
            //发送邮件
            mailSender.send(message);
            result.setCode(200);
            result.setMessage("送信に成功");
            result.setSuccess(true);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(false);
            return result;
        }
    }

    /**
     * 验证码
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public Result<?> sendMailCode(SendEmailCode sendEmail) {
        Result<?> result = new Result<>();
        try {
            //创建SimpleMailMessage对象
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件发送人
            message.setFrom(from);
            //邮件接收人
            message.setTo(sendEmail.getTo());
            //邮件主题
            message.setSubject("修改密码验证");
            //邮件内容
            String code = getRandomString(6);
            redisUtil.set(sendEmail.getTo()+":updatePasswordCode",code,300);
            String text = "您的验证码为：" + code+",请妥善保管，该验证码5分钟内有效！";
            message.setText(text);
            //发送邮件
            mailSender.send(message);
            result.setCode(200);
            result.setMessage("送信に成功");
            result.setSuccess(true);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(false);
            return result;
        }
    }

    //生成随机字符串
    public String getRandomString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * html邮件
     *
     * @param to      收件人,多个时参数形式 ："xxx@xxx.com,xxx@xxx.com,xxx@xxx.com"
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人,设置多个收件人地址
            InternetAddress[] internetAddressTo = InternetAddress.parse(to);
            messageHelper.setTo(internetAddressTo);
            //messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }
}
