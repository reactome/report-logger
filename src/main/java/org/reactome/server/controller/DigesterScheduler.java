package org.reactome.server.controller;

import org.reactome.server.service.SearchDigesterService;
import org.reactome.server.service.TargetDigesterService;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@Component
@EnableScheduling
@RequestMapping("/digester")
public class DigesterScheduler {

    private final String mailTo;
    private TargetDigesterService targetDigesterService;
    private SearchDigesterService searchDigesterService;
    private MailService mailService;
    private String hostname;

    public DigesterScheduler(@Value("${mail.report}") String mailTo,
                             @Value("${mail.report.hostname}") String hostname) {
        this.mailTo = mailTo;
        this.hostname = hostname;
    }

    @Scheduled(cron = "0 0 12 * * SAT") // every Saturday at midday
    public void weeklyReport() {
        if (!sendReport()) return;
        Mail mail = new Mail("Reactome Report <noreply@reactome.org>", mailTo, "[Search] Weekly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Weekly report");
        model.put("targetSummary", targetDigesterService.findLastWeekTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastWeekTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastWeekSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastWeekSearchesByDateTermAndIp());
        model.put("logo", getLogo());
        mail.setModel(model);

        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 * *") // every day 1 at 01AM
    public void monthlyReport() {
        if (!sendReport()) return;
        Mail mail = new Mail("Reactome Report  <noreply@reactome.org>", mailTo, "[Search] Monthly report", "search-target.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Monthly report");
        model.put("targetSummary", targetDigesterService.findLastMonthTargetsByDateTerm());
        model.put("targetsByIp", targetDigesterService.findLastMonthTargetsByDateTermAndIp());
        model.put("searchSummary", searchDigesterService.findLastMonthSearchesByDateTerm());
        model.put("searchByIp", searchDigesterService.findLastMonthSearchesByDateTermAndIp());
        model.put("logo", getLogo());
        mail.setModel(model);

        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/weekly201801/{name}/{host:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testWeeklyReport(@PathVariable(name = "name") String name, @PathVariable(name = "host") String host) {
        if (host.equalsIgnoreCase("reactome.org") || host.equalsIgnoreCase("ebi.ac.uk")) {
            String mailTo = name + "@" + host;
            Mail mail = new Mail("Reactome Report <noreply@reactome.org>", mailTo, "[Search] Weekly report TEST", "search-target.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Weekly report");
            model.put("targetSummary", targetDigesterService.findLastWeekTargetsByDateTerm());
            model.put("targetsByIp", targetDigesterService.findLastWeekTargetsByDateTermAndIp());
            model.put("searchSummary", searchDigesterService.findLastWeekSearchesByDateTerm());
            model.put("searchByIp", searchDigesterService.findLastWeekSearchesByDateTermAndIp());
            model.put("logo", getLogo());
            mail.setModel(model);
            mailService.sendEmail(mail);
        }
    }

    @GetMapping(value = "/lastmonth201801/{name}/{host:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testMonthlyReport(@PathVariable(name = "name") String name, @PathVariable(name = "host") String host) {
        if (host.equalsIgnoreCase("reactome.org") || host.equalsIgnoreCase("ebi.ac.uk")) {
            String mailTo = name + "@" + host;
            Mail mail = new Mail("Reactome Report  <noreply@reactome.org>", mailTo, "[Search] Monthly report TEST", "search-target.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Monthly report");
            model.put("targetSummary", targetDigesterService.findLastMonthTargetsByDateTerm());
            model.put("targetsByIp", targetDigesterService.findLastMonthTargetsByDateTermAndIp());
            model.put("searchSummary", searchDigesterService.findLastMonthSearchesByDateTerm());
            model.put("searchByIp", searchDigesterService.findLastMonthSearchesByDateTermAndIp());
            model.put("logo", getLogo());
            mail.setModel(model);
            mailService.sendEmail(mail);
        }
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
    private String getLogo() {
        return "https://reactome.org/templates/favourite/images/logo/logo.png";
    }

    private boolean sendReport() {
        try {
            return hostname.equalsIgnoreCase(InetAddress.getLocalHost().getHostName());
        } catch (Throwable t) {
            return false;
        }
    }
}
