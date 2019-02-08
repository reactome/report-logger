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
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.time.LocalDate;
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
    private final String DATE_FORMAT = "yyyy-MM-dd";
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
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String fromDate = lastWeek.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String subject = String.format(mailSubject, "Weekly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Weekly", fromDate, today));
        getEditorialReports(model, lastWeek);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 * *") // every day 1 at 01AM
    public void monthlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        String fromDate = lastMonth.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String subject = String.format(mailSubject, "Monthly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Monthly", fromDate, today));
        getEditorialReports(model, lastMonth);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 Jan,May,Sep *") // every day 1 at 01AM in Jan, May and Sep
    public void quarterlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastQuarter = LocalDateTime.now().minusMonths(4);
        String fromDate = lastQuarter.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String subject = String.format(mailSubject, "Quarterly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Quarterly", fromDate, today));
        getEditorialReports(model, lastQuarter);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 1 1 Jan *") // every day 1 at 01AM in January
    public void yearlyReport() {
        if (!matchesHostame()) return;
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);
        String fromDate = lastYear.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String subject = String.format(mailSubject, "Yearly", fromDate, today);
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Yearly", fromDate, today));
        getEditorialReports(model, lastYear);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/weekly/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testWeeklyReport(@PathVariable(name = "email") String email) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String fromDate = lastWeek.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        Mail mail = new Mail(mailFrom, email, "[Search] Weekly report TEST", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Weekly", fromDate, today));
        getEditorialReports(model, lastWeek);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping(value = "/monthly/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testMonthlyReport(@PathVariable(name = "email") String email) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        String fromDate = lastMonth.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        Mail mail = new Mail(mailFrom, email, "[Search] Monthly report TEST", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Monthly", fromDate, today));
        getEditorialReports(model, lastMonth);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }


    /**
     * Example url - http://localhost/report/digester/custom/johndoe@ebi.ac.uk?fromYMD=2018-01-01&toYMD=2018-12-31&label=Yearly
     *
     * @param email    to whom you want to send it
     * @param fromDate format 2019-01-01
     * @param toDate   format 2019-01-31
     * @param label    Free text to appear in the subject, yearly, weekly
     */
    @GetMapping(value = "/custom/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void testCustomReport(@PathVariable(name = "email") String email, @RequestParam(name = "fromYMD") String fromDate, @RequestParam(name = "toYMD") String toDate, @RequestParam(name = "label") String label) {
        Mail mail = new Mail(mailFrom, email, "[Search] " + label + " report", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, label, fromDate, toDate));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime fromLDT = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime toLDT = LocalDate.parse(toDate, formatter).atStartOfDay();

        getCustomReport(model, fromLDT, toLDT);
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

    private void getEditorialReports(Map<String, Object> model, LocalDateTime date) {
        List<TargetDigester> targetSummary = targetDigesterService.findTargets(date);
        List<TargetDigester> searchSummary = searchDigesterService.findSearches(date);
        prepareReportModel(model, targetSummary, searchSummary);
    }

    private void getCustomReport(Map<String, Object> model, LocalDateTime fromLDT, LocalDateTime toLDT) {
        List<TargetDigester> targetSummary = targetDigesterService.findTargetsByDates(fromLDT, toLDT);
        List<TargetDigester> searchSummary = searchDigesterService.findSearchesByDates(fromLDT, toLDT);
        prepareReportModel(model, targetSummary, searchSummary);
    }

    private void prepareReportModel(Map<String, Object> model, List<TargetDigester> targetSummary, List<TargetDigester> searchSummary) {
        List<TargetDigester> targetRelevantSummary = new ArrayList<>();
        List<TargetDigester> targetSingleUsersSummary = new ArrayList<>();

        for (TargetDigester targetDigester : targetSummary) {
            if (targetDigester.getUniqueIPs() == 1) {
                targetSingleUsersSummary.add(targetDigester);
            } else {
                targetRelevantSummary.add(targetDigester);
            }
        }

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
