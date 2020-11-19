package com.duing.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailUtil {


    public static String myEmailAccount = "2491638831@qq.com";
    public static String myEmailPassword = "oowuzcohixgtecgb";

    public static String myEmailSMTPHost = "smtp.qq.com";

    public static void main(String[] args) throws Exception {

        // 创建参数
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.host", myEmailSMTPHost);
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", "465");

        // 创建会话   设置debug模式  可以看到日志
        Session session = Session.getInstance(prop);
        session.setDebug(true);

        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(myEmailAccount, "学习啦", "UTF-8"));
        // 收件人   如果是抄送  类型设置CC  密送 BCC
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress("lanluo_bingzi@126.com", "语晴同学", "UTF-8"));

        message.setSubject("来自语晴老师的邮件", "UTF-8");
        message.setContent("hello how are you", "text/html;charset=UTF-8");

        message.setSentDate(new Date());
        message.saveChanges();  //可以  以eml的格式保存


        // 传输
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }
}
