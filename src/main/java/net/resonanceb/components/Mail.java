package net.resonanceb.components;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
    /**
     * Send an email based on the provided contents
     * 
     * @param server mail server
     * @param from address
     * @param to address
     * @param subject text
     * @param body text
     * @return boolean of message transmission success
     * @throws MessagingException
     */
    public static boolean sendMail(String server, String from, String to, String subject, String body) throws MessagingException {
        // Setup system props
        Properties sysProps = System.getProperties();
        sysProps.put("mail.smtp.host", server);

        // Create mail mime messsage
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(sysProps));
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart bodypart = new MimeBodyPart();

        // Authenticator a;  // a way to authenticate via smtp.  seems to be only username / pass

        // set message contents
        bodypart.setContent(body, "text/html");
        multipart.addBodyPart(bodypart);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(multipart);

        // transport!!
        Transport.send(message);

        return true;
    }
}