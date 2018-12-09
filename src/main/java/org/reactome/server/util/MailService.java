package org.reactome.server.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * Mail Service
 * Created by gsviteri on 15/10/2015.
 */
@Service
public class MailService {

    private JavaMailSender mailSender;
    private Configuration fmConfiguration;

    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            mail.setContent(getContentFromTemplate(mail));
            mimeMessageHelper.setText(mail.getContent(), true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(Mail mail) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate(mail.getTemplate()), mail.getModel()));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setFmConfiguration(Configuration fmConfiguration) {
        this.fmConfiguration = fmConfiguration;
    }

}
