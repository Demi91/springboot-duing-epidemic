package com.duing.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    public void send() {

        System.out.println("发送邮件");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("来自渡一课程的邮件");
        message.setText("不知道说什么的正文");

        message.setTo("lanluo_bingzi@126.com");
        message.setFrom("2491638831@qq.com");

        mailSender.send(message);
    }

}
