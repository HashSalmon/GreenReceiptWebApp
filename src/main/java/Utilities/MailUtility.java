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

import java.text.ParseException;

/**
 * Holds the functions necessary to send emails to the user, providing them with receipts, reports, information, etc.
 *
 * @author Jordan Wanlass
 */
public class MailUtility {

    private static final String FROM = "greenreceiptteam@gmail.com";
    private static final String PASSWORD = "N0teP@djwGm@ilMay14";

    /**
     * Sends an email with the information for a specific receipt along with an image associated with the receipt.
     *
     * @param subject The subject of the email
     * @param receipt The receipt object.  Its used to populate the email with information.
     * @throws ParseException
     */
    public void sendMail(String subject, Receipt receipt) throws ParseException {
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
            String content = "Store: " + receipt.getStore().getCompany().getName() + "\n";
            for(ReceiptItem item: receipt.getReceiptItems()) {
                content += item.getItemName() + " " + item.getPrice() + "\n";
            }
            content += "Return Date: " + receipt.getReturnDate() + "\n";
            content += "Total: " + receipt.getTotal();
            helper.setText(content);

//            FileSystemResource file = new FileSystemResource(getAttatchmentUrl(receipt.getStore().getCompany().getName(), receipt.getTotal().toString()));
//            helper.addAttachment(file.getFilename(), file);
            mailSender1.send(message);
            System.out.println("Mail sent successfully.");
        }catch (MessagingException e) {
            throw new MailParseException(e);
        }

    }

    public String getAttatchmentUrl(String storeName, String total) {

        String url = "/Users/jordanwanlass/Desktop/receiptpics/";

        if("Smiths".equals(storeName) && "$5.00".equals(total)) {
            url += "smiths5.png";
        } else if ("Smiths".equals(storeName) && "$60.00".equals(total)) {
            url += "smiths60.png";
        } else if ("Smiths".equals(storeName) && "$35.00".equals(total)) {
            url += "smiths35.png";
        } else if ("Best Buy".equals(storeName)) {
            url += "bestbuy.png";
        } else if ("Megaplex".equals(storeName)) {
            url += "megaplex.png";
        } else if ("Rodizio's Grill".equals(storeName)) {
            url += "rodizios.png";
        } else if ("Zumiez".equals(storeName)) {
            url += "zumiez.png";
        } else if ("Chevron".equals(storeName)) {
            url += "chevron.png";
        }
        return url;
    }
}
