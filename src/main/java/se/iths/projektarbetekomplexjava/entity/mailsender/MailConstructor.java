package se.iths.projektarbetekomplexjava.entity.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.mail.SimpleMailMessage;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Locale;

@Component
public class MailConstructor {

    private Environment env;

    private TemplateEngine templateEngine;

    public MailConstructor(Environment env, TemplateEngine templateEngine) {
        this.env = env;
        this.templateEngine = templateEngine;
    }

    public SimpleMailMessage emailConfirmation(
            String contextPath, Locale locale,Customer customer) {

        String url = contextPath + "/signup";
        String message = "\n\n" + "thanks for registration";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(customer.getEmail());
        email.setSubject("sign up completing");
        email.setText(url +" "+message);
        return email;

    }

    public MimeMessagePreparator OrderConfirmationEmail(Customer customer, Orders orders) {
        Context context = new Context();
        context.setVariable("order", orders);
        context.setVariable("customer", customer);
        context.setVariable("cartItem", orders.getCartItemList());
        String text = "hello "+customer.getFirstName()+"thanks for ordering from BokHandel your payment is 2312.00 " +
                "SEK expire date for payment is 12-01-2022. "+context.getVariableNames().toString();

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator(){

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(customer.getEmail());
                email.setSubject(" order confirmation."+orders.getId());
                email.setText(text);
            }
        };

        return messagePreparator;
    }

}



