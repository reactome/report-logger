package org.reactome.server.controller;

import org.reactome.server.service.TargetDigesterService;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@Component
@RequestMapping("/digester")
public class DigesterScheduler {

    private TargetDigesterService targetDigesterService;
    private MailService mailService;
    private final String mailTo;

    public DigesterScheduler(@Value("${mail.report}") String mailTo) {
        this.mailTo = mailTo;
    }

//    @Scheduled(cron = "0 7 * * MON") // every Monday at 07AM
    @GetMapping(value="/sendmail")
    @ResponseStatus(HttpStatus.OK)
    public String weeklyReport() {
//        List<TargetDigester> targets = targetDigesterService.findTargetByDateTerm();

        Mail mail = new Mail("definefrom@reactome.org", mailTo, "[Search] Weekly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Weekly report");
        model.put("targetSummary", targetDigesterService.findLastWeekTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastWeekTargetsByDateTermAndIp());
        mail.setModel(model);

        mailService.sendEmail(mail);
        return "redirect:/lastmonth";
    }

    //@Scheduled(cron = "0 7 1 * *") // every Monday at 07AM
    @GetMapping(value="/lastmonth")
    @ResponseStatus(HttpStatus.OK)
    public String monthlyReport() throws IOException {

        Mail mail = new Mail("definefrom@reactome.org", mailTo, "[Search] Monthly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Monthly report");
        model.put("targetSummary", targetDigesterService.findLastMonthTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastMonthTargetsByDateTermAndIp());

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("templates/logo.png");
        if (resource != null) {
            byte[] data = Files.readAllBytes(new File(resource.getFile()).toPath());
            byte[] encoded = Base64.getEncoder().encode(data);
            String imgDataAsBase64 = new String(encoded);
            String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
            model.put("logo", imgAsBase64);
        }
        mail.setModel(model);

        mailService.sendEmail(mail);
        return "success";
    }

    @Autowired
    public void setTargetDigesterService(TargetDigesterService targetDigesterService) {
        this.targetDigesterService = targetDigesterService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
