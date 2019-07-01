package org.reactome.server.controller;

import org.reactome.server.service.OrcidDigesterService;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings({"Duplicates", "FieldCanBeLocal"})
@Component
@EnableScheduling
@RequestMapping("/digester/orcid")
public class OrcidReportScheduler {

    private final String MAIL_TEMPLATE = "orcid.ftl";
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String mailFrom;
    private final String mailTo;
    private String mailSubject = "[ORCID] %s claiming report [%s to %s]";
    private String dailyMailSubject = "[ORCID] Claiming report for [%s]";
    private String mailHeader = "%s report [%s to %s]";
    private String dailyMailHeader = "Claiming report for %s";
    private MailService mailService;
    private OrcidDigesterService orcidDigesterService;
    private String hostname;

    public OrcidReportScheduler(@Value("${mail.report.from}") String mailFrom,
                                @Value("${mail.report.to}") String mailTo,
                                @Value("${mail.report.hostname}") String hostname) {
        this.mailFrom = "Reactome Report <" + mailFrom + ">";
        this.mailTo = mailTo;
        this.hostname = hostname;
    }

    @GetMapping("/daily")
    @ResponseStatus(HttpStatus.OK)
    public void dailyReport() {
        if (!matchesHostame()) return;
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String subject = String.format(dailyMailSubject, yesterday.format(ofPattern(DATE_FORMAT)));
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(dailyMailHeader, yesterday.format(ofPattern(DATE_FORMAT))));
        model.put("label1", "Claiming for today");
        model.put("todayClaimed", orcidDigesterService.findBySingleDate(yesterday));
        model.put("label2", "Who has claimed so far");
        model.put("totalClaimed", orcidDigesterService.findAllClaimedByDate(yesterday));
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @GetMapping("/weekly")
    @ResponseStatus(HttpStatus.OK)
    public void weeklyReport() {
        if (!matchesHostame()) return;
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        LocalDateTime today = LocalDateTime.now();
        String subject = String.format(mailSubject, "Weekly", lastWeek.format(ofPattern(DATE_FORMAT)), today.format(ofPattern(DATE_FORMAT)));
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Weekly", lastWeek.format(ofPattern(DATE_FORMAT)), today.format(ofPattern(DATE_FORMAT))));
        model.put("label1", "Claiming from last week");
        model.put("todayClaimed", orcidDigesterService.findByDates(lastWeek, today));
        model.put("label2", "Who has claimed so far");
        model.put("totalClaimed", orcidDigesterService.findAllClaimed());
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Scheduled(cron = "0 0 12 1 * *") // every day 1 at 01AM
    @ResponseStatus(HttpStatus.OK)
    public void monthlyReport() {
        if (!matchesHostame()) return;
        LocalDateTime firstDayOfTheMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime lastDayOfTheMonth = LocalDateTime.now().minusDays(1);
        String subject = String.format(mailSubject, "Monthly", firstDayOfTheMonth.format(ofPattern(DATE_FORMAT)), lastDayOfTheMonth.format(ofPattern(DATE_FORMAT)));
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, "Monthly", firstDayOfTheMonth.format(ofPattern(DATE_FORMAT)), lastDayOfTheMonth.format(ofPattern(DATE_FORMAT))));
        model.put("label1", "Claiming from last Month");
        model.put("todayClaimed", orcidDigesterService.findByDates(firstDayOfTheMonth, lastDayOfTheMonth));
        model.put("label2", "Who has claimed so far");
        model.put("totalClaimed", orcidDigesterService.findAllClaimed());
        mail.setModel(model);
        mailService.sendEmail(mail);
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setOrcidDigesterService(OrcidDigesterService orcidDigesterService) {
        this.orcidDigesterService = orcidDigesterService;
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
}
