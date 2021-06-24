package org.reactome.server;

import org.junit.jupiter.api.Test;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MailServiceTest {

    public static final String MAIL_TEMPLATE = "test-mail.ftl";
    public static final String MAIL_FROM = "test@reactome.org";
    public static final String MAIL_TO = "orkeliot@gmail.com";
    @Autowired
    private MailService mailService;

    @Test
    public void sendMailTest() {
        Mail mail = new Mail(MAIL_FROM, MAIL_TO, "Test mail sender", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", "Test mail sender");
        mail.setModel(model);
        mailService.sendEmail(mail);
    }
}
