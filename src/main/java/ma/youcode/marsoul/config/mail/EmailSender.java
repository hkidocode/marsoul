package ma.youcode.marsoul.config.mail;

import java.util.Map;

public interface EmailSender {
    void sendEmail(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel);
}
