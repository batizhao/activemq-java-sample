package me.batizhao.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.*;

/**
 * @author: batizhao
 * @since: 11-11-25 下午2:02
 */
public class SendMail {

    public static void main(String[] args) throws MessagingException, IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("from_address", "Bati Zhao <zhaobati@gmail.com>");
        map.put("to_address", "zhaobati@gmail.com");
        map.put("subject", "Test for ActiveMQ");
        map.put("content", "Hi, This is test for ActiveMQ.");

        SendMail sendMail = new SendMail();
        sendMail.sendMail(map);
    }

    public boolean sendMail(Map<String, String> map) throws MessagingException, IOException {

        Properties props = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream("src/main/resources/mail.properties"));
        props.load(in);

        /*props.put("mail.smtp.host", SMTP_MAIL_HOST);
        props.put("mail.smtp.port", SMTP_MAIL_PORT);
        props.put("mail.smtp.auth", "true");*/

        final String username = props.getProperty("mail.smtp.user");
        final String password = props.getProperty("mail.smtp.password");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(map.get("from_address")));
        message.addRecipients(Message.RecipientType.TO, map.get("to_address"));
        message.setSentDate(new Date());
        message.setSubject(map.get("subject"));
        //message.setSubject(MimeUtility.encodeText(MAIL_SUBJECT, "gbk", "B"));

        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(map.get("content"), "text/html;charset=utf-8");
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);
        Transport.send(message);

        System.out.println("Send mail success.");

        return true;
    }

}
