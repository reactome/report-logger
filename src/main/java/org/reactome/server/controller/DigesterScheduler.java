package org.reactome.server.controller;

import org.reactome.server.service.SearchDigesterService;
import org.reactome.server.service.TargetDigesterService;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final String mailTo;
    private TargetDigesterService targetDigesterService;
    private SearchDigesterService searchDigesterService;
    private MailService mailService;

    public DigesterScheduler(@Value("${mail.report}") String mailTo) {
        this.mailTo = mailTo;
    }

    @Scheduled(cron = "0 12 * * SAT") // every Monday at 07AM
    @ResponseStatus(HttpStatus.OK)
    public void weeklyReport() {
        Mail mail = new Mail("Reactome Report <noreply@reactome.org>", mailTo, "[Search] Weekly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Weekly report");
        model.put("targetSummary", targetDigesterService.findLastWeekTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastWeekTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastWeekSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastWeekSearchesByDateTermAndIp());
        model.put("logo", getLogoAsBase64());
        mail.setModel(model);

        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 1 1 * *") // every Monday at 01AM
    @ResponseStatus(HttpStatus.OK)
    public void monthlyReport() {
        Mail mail = new Mail("Reactome Report  <noreply@reactome.org>", mailTo, "[Search] Monthly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Monthly report");
        model.put("targetSummary", targetDigesterService.findLastMonthTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastMonthTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastMonthSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastMonthSearchesByDateTermAndIp());
        model.put("logo", getLogoAsBase64());
        mail.setModel(model);

        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/weekly201801/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void testWeeklyReport(@PathVariable(name = "name") String name) {
        String mailTo = name + "@ebi.ac.uk";
        Mail mail = new Mail("Reactome Report <noreply@reactome.org>", mailTo, "[Search] Weekly report TEST", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Weekly report");
        model.put("targetSummary", targetDigesterService.findLastWeekTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastWeekTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastWeekSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastWeekSearchesByDateTermAndIp());
        model.put("logo", getLogoAsBase64());
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/lastmonth201801/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void testMonthlyReport(@PathVariable(name = "name") String name) {
        String mailTo = name + "@ebi.ac.uk";
        Mail mail = new Mail("Reactome Report  <noreply@reactome.org>", mailTo, "[Search] Monthly report TEST", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Monthly report");
        model.put("targetSummary", targetDigesterService.findLastMonthTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastMonthTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastMonthSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastMonthSearchesByDateTermAndIp());
        model.put("logo", getLogoAsBase64());
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Autowired
    public void setTargetDigesterService(TargetDigesterService targetDigesterService) {
        this.targetDigesterService = targetDigesterService;
    }

    @Autowired
    public void setSearchDigesterService(SearchDigesterService searchDigesterService) {
        this.searchDigesterService = searchDigesterService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Get logo stored in resources and encode to Base64. If the logo ins't found then get the logo
     * directly from the web site.
     */
    private String getLogoAsBase64() {
        String imgAsBase64 = "https://reactome.org/templates/favourite/images/logo/logo.png";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("templates/logo.png");
            if (resource != null) {
                byte[] data = Files.readAllBytes(new File(resource.getFile()).toPath());
                byte[] encoded = Base64.getEncoder().encode(data);
                String imgDataAsBase64 = new String(encoded);
                imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
            }
        } catch (IOException e) {
            // nothing here
        }
        return imgAsBase64;

    }
}
