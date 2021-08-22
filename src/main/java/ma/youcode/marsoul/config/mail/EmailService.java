package ma.youcode.marsoul.config.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendEmail(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);
            mimeMessageHelper.setText(springTemplateEngine.process(thymeleafFileName, thymeleafContext), true);
            mimeMessageHelper.setTo(email.split("[,;]"));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            throw new IllegalStateException("Failed to send email");
        }

    }
}
