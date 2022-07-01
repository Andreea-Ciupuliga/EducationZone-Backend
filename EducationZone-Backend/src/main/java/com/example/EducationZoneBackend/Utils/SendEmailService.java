package com.example.EducationZoneBackend.Utils;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;

import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;


@Service
public class SendEmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String body, String topic) {
        System.out.println("sending email to: " + to);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("Education-Zone <educationzoneapp@gmail.com>");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);

        System.out.println("email send to " + to);
    }

//    private final SmtpApi apiInstance;
//
//    @Autowired
//    public SendEmailService() {
//
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
//        apiKey.setApiKey("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");
//
//        this.apiInstance = new SmtpApi();
//    }
//
//    @SneakyThrows
//    @Async
//    @Retryable(value = {ApiException.class})
//    public void sendEmail(String to, String body, String topic) {
//        System.out.println("sending email");
//
//        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
//
//        SendSmtpEmailSender sender = new SendSmtpEmailSender();
//        sender.setEmail("educationzoneapp@gmail.com");
//        sender.setName("Education-Zone");
//
//
//        SendSmtpEmailTo receiver = new SendSmtpEmailTo();
//        receiver.setEmail(to);
//
//        sendSmtpEmail.setSender(sender);
//        sendSmtpEmail.setTo(Collections.singletonList(receiver));
//        sendSmtpEmail.setSubject(topic);
//        sendSmtpEmail.setTextContent(body);
//
//        CreateSmtpEmail result =apiInstance.sendTransacEmail(sendSmtpEmail);
//        System.out.println(result);
//
//        System.out.println("email send");
//    }


}
