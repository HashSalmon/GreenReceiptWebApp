package Utilities;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.springapp.mvc.Receipt;
import com.springapp.mvc.ReceiptItem;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtility {

    private static final String FROM = "greenreceiptteam@gmail.com";
    private static final String PASSWORD = "N0teP@djwGm@ilMay14";

    public void sendMail(String subject, Receipt receipt) {
        JavaMailSenderImpl mailSender1 = new JavaMailSenderImpl();
        mailSender1.setHost("smtp.gmail.com");
        mailSender1.setPort(587);
        mailSender1.setUsername(FROM);
        mailSender1.setPassword(PASSWORD);
        mailSender1.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender1.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        MimeMessage message = mailSender1.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(FROM);
            helper.setTo("jwanlass8@gmail.com");
            helper.setSubject(subject);
            String content = "Store: " + receipt.getStore() + "\n";
            for(ReceiptItem item: receipt.getItems()) {
                content += item.getName() + " " + item.getPrice() + "\n";
            }
            content += "Return Date: " + receipt.getReturnDate() + "\n";
            content += "Total: " + receipt.getTotal();
            helper.setText(content);

            FileSystemResource file = new FileSystemResource("/Users/jordanwanlass/Desktop/test.png");
            helper.addAttachment(file.getFilename(), file);
            mailSender1.send(message);
            System.out.println("Mail sent successfully.");
        }catch (MessagingException e) {
            throw new MailParseException(e);
        }

    }
}
