package com.blockchain.commune.utils;

import freemarker.template.Template;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 邮箱工具类
 */
public class MailUtil {

    private static JavaMailSenderImpl mailSender;

    static {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.exmail.qq.com");
        mailSender.setUsername("register@cococo.org");
        mailSender.setPassword("Abcd1234!");
        mailSender.setPort(25);
        mailSender.setDefaultEncoding("UTF-8");
        //加认证机制
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);
        mailSender.setJavaMailProperties(javaMailProperties);
    }

    private static String sender = "register@cococo.org";

    /**
     * 发送简单文本邮件
     * @param to 收件人，一组，不能为空
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws Exception
     */
    public static void sendSimpleMail(String[] to, String subject, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送Html邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param html 邮件内容，html
     * @throws Exception
     */
    public static void sendHtmlMail(String[] to, String subject, String html) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param files 附件,key请将文件的扩展名也加上，否则无法预览。如：picture.jpg
     * @throws Exception
     */
    public static void sendAttachmentsMail(String[] to, String subject, String content, Map<String, File> files) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);
        //注意项目路径问题，自动补用项目路径
        //FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
        //加入邮件
        //helper.addAttachment("图片.jpg", file);
        Set<Map.Entry<String,File>> fileSet = files.entrySet();
        for (Map.Entry f : fileSet) {
            helper.addAttachment((String) f.getKey(), (File) f.getValue());
        }
        mailSender.send(message);
    }

    /**
     * 发送带静态资源的邮件<br />
     * 将文件内容显示在邮件内容中，一般为图片，如果为文件，有待测试
     * @param to 收件人
     * @param subject 邮件主题
     * @param html html文本
     * @param files 要加入html中的静态资源<br />
     * example: <br />
     * html: "带静态资源的邮件内容 图片:img src='cid:dog'    img src='cid:pig'"<br />
     * files: [{key:dog, value: dog.jpg},{key:pig, value:pig.jpg}]<br />
     * files中的key要和html中的cid一一对应。
     * @throws Exception
     */
    public static void sendInlineMail(String[] to, String subject, String html, Map<String, File> files) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
        //"<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>"
        helper.setText(html, true);
        Set<Map.Entry<String,File>> fileSet = files.entrySet();
        for (Map.Entry f : fileSet) {
            helper.addInline((String) f.getKey(), (File) f.getValue());
        }
        //FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
        //helper.addInline("picture",file);
        mailSender.send(message);
    }

    /**
     * 发送模版邮件：模版使用的是freemarker
     * @param to 收件人
     * @param subject 邮件主题
     * @param template 模版
     * @param model 模版参数值<br />
     *              example：<br />
     *              Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.html");<br />
     *              main.html:"你好， ${username}, 这是一封模板邮件!"<br />
     *              model:{key: username, value: zhangsan}
     *
     * @throws Exception
     */
    public static void sendTemplateMail(String[] to, String subject, Template template, Map<String, Object> model) throws Exception {
        MimeMessage message = null;
        message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        //修改 application.properties 文件中的读取路径
//            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//            configurer.setTemplateLoaderPath("classpath:templates");
        //读取 html 模板
        //Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(html, true);
        mailSender.send(message);
    }

}
