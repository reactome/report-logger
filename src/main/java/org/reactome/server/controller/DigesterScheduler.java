package org.reactome.server.controller;

import org.reactome.server.domain.TargetDigester;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("Duplicates")
@Component
@EnableScheduling
@RequestMapping("/digester")
public class DigesterScheduler {

    private final String MAIL_TEMPLATE = "search-target.ftl";
    private final String mailFrom;
    private final String mailTo;
    private String mailSubject = "[Search] %s report [%s to %s]";
    private TargetDigesterService targetDigesterService;
    private SearchDigesterService searchDigesterService;
    private MailService mailService;
    private String hostname;
    private String mailHeader = "%s report [%s to %s]";

    public DigesterScheduler(@Value("${mail.report.from}") String mailFrom,
                             @Value("${mail.report.to}") String mailTo,
                             @Value("${mail.report.hostname}") String hostname) {
        this.mailFrom = "Reactome Report <" + mailFrom + ">";
        this.mailTo = mailTo;
        this.hostname = hostname;
    }

    @Scheduled(cron = "0 0 12 * * SAT") // every Saturday at midday
    public void weeklyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String fromDate = lastWeek.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String subject = String.format(mailSubject, "Weekly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Weekly", fromDate, today));
        prepareReportList(model, lastWeek);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 * *") // every day 1 at 01AM
    public void monthlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        String fromDate = lastMonth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String subject = String.format(mailSubject, "Monthly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Monthly", fromDate, today));
        prepareReportList(model, lastMonth);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 Jan,May,Sep *") // every day 1 at 01AM in Jan, May and Sep
    public void quarterlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastQuarter = LocalDateTime.now().minusMonths(4);
        String fromDate = lastQuarter.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String subject = String.format(mailSubject, "Quarterly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Quarterly", fromDate, today));
        prepareReportList(model, lastQuarter);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 Jan *") // every day 1 at 01AM in January
    public void yearlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);
        String fromDate = lastYear.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String subject = String.format(mailSubject, "Yearly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Yearly", fromDate, today));
        prepareReportList(model, lastYear);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/weekly/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testWeeklyReport(@PathVariable(name = "email") String email) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String fromDate = lastWeek.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Mail mail = new Mail(mailFrom, email, "[Search] Weekly report TEST", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Weekly", fromDate, today));
        prepareReportList(model, lastWeek);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/monthly/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testMonthlyReport(@PathVariable(name = "email") String email) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        String fromDate = lastMonth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Mail mail = new Mail(mailFrom, email, "[Search] Monthly report TEST", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Monthly", fromDate, today));
        prepareReportList(model, lastMonth);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 25 10 * * TUE", zone = "Europe/London") // every Saturday at midday
    public void cronReport() throws Exception {
        if (!matchesHostame()) {
            System.out.println("Running cron report test... hosts are not the same " + InetAddress.getLocalHost().getHostName());
            return;
        }
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String fromDate = lastWeek.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String subject = String.format(mailSubject, "Cron Test to help@reactome.org", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Cron Test help@reactome.org", fromDate, today));
        model.put("targetTotal", 1);
        model.put("targetRelevantSummary", new ArrayList<>());
        model.put("targetSingleSummary", new ArrayList<>());

        model.put("searchSummaryTotal", 1);
        model.put("searchRelevantSummary", new ArrayList<>());
        model.put("searchSingleUsersSummary", new ArrayList<>());

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
     * hostname has the server name from where we want to send the report.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean matchesHostame() {
        try {
            return hostname.equalsIgnoreCase(InetAddress.getLocalHost().getHostName());
        } catch (Throwable t) {
            return false;
        }
    }

    private void prepareReportList(Map<String, Object> model, LocalDateTime date) {
        List<TargetDigester> targetSummary = targetDigesterService.findTargets(date);
        List<TargetDigester> targetRelevantSummary = new ArrayList<>();
        List<TargetDigester> targetSingleUsersSummary = new ArrayList<>();

        for (TargetDigester targetDigester : targetSummary) {
            if (targetDigester.getUniqueIPs() == 1) {
                targetSingleUsersSummary.add(targetDigester);
            } else {
                targetRelevantSummary.add(targetDigester);
            }
        }

        List<TargetDigester> searchSummary = searchDigesterService.findSearches(date);
        List<TargetDigester> searchRelevantSummary = new ArrayList<>();
        List<TargetDigester> searchSingleUsersSummary = new ArrayList<>();

        for (TargetDigester targetDigester : searchSummary) {
            if (targetDigester.getUniqueIPs() == 1) {
                searchSingleUsersSummary.add(targetDigester);
            } else {
                searchRelevantSummary.add(targetDigester);
            }
        }

        model.put("targetTotal", targetSummary.size());
        model.put("targetRelevantSummary", targetRelevantSummary);
        model.put("targetSingleSummary", targetSingleUsersSummary);

        model.put("searchSummaryTotal", searchSummary.size());
        model.put("searchRelevantSummary", searchRelevantSummary);
        model.put("searchSingleUsersSummary", searchSingleUsersSummary);
    }
}
