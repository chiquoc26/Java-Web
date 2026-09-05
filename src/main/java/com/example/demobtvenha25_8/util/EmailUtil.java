package com.example.demobtvenha25_8.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    private static final String FROM_EMAIL   = "lequoc41126@gmail.com";
    private static final String APP_PASSWORD = "vxno biqg debt umky";
    private static final String SMTP_HOST    = "smtp.gmail.com";
    private static final int    SMTP_PORT    = 587;

    public static void sendOtp(String toEmail, String otp, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            SMTP_HOST);
        props.put("mail.smtp.port",            String.valueOf(SMTP_PORT));
        props.put("mail.smtp.ssl.protocols",   "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL, "WebApp", "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            message.setFrom(new InternetAddress(FROM_EMAIL));
        }
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject, "UTF-8");
        message.setContent(buildEmail(otp), "text/html; charset=UTF-8");

        Transport.send(message);
    }

    private static String buildEmail(String otp) {
        return "<!DOCTYPE html>"
             + "<html><head><meta charset='UTF-8'></head><body>"
             + "<p>Ma OTP cua ban la:</p>"
             + "<h2>" + otp + "</h2>"
             + "<p>Ma co hieu luc trong 5 phut.</p>"
             + "<p>Neu ban khong yeu cau ma nay, vui long bo qua email nay.</p>"
             + "</body></html>";
    }
}
