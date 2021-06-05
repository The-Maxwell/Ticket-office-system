package services.email;

import services.email.IEmailService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailServiceImpl implements IEmailService {

    public String sendReportToEmail(String to, String header, String textMessage, String pathToReport, String filename) {
        String result = null;

        final String SENDER_EMAIL_ADDRESS = "ticketoffice.reports.sender@gmail.com";
        final String SENDER_EMAIL_PASSWORD = "reP_40ku_Csdr";
        final String SENDER_HOST = "smtp.gmail.com";
        final String SENDER_PORT = "587";
        final String RECEIVER_EMAIL_ADDRESS = to;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", SENDER_HOST);
        properties.put("mail.smtp.port", SENDER_PORT);
        properties.put("mail.from", SENDER_EMAIL_ADDRESS);
        properties.put("mail.smtp.password", SENDER_EMAIL_PASSWORD);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        //Get the default Session object.
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
                    }
                });
        try {
            //Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            //Set From: header field of the header.
            message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
            //Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Set Subject: header field
            message.setSubject(header);
            //Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            //Fill the message
            messageBodyPart.setText(textMessage);
            //Create a multipar message
            Multipart multipart = new MimeMultipart();
            //Set text message part
            multipart.addBodyPart(messageBodyPart);
            //Part two is attachment
            messageBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(pathToReport);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            //Send the complete message parts
            message.setContent(multipart);

            //Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            result = mex.getMessage();
        }
        return result;
    }
}
