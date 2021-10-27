package org.reactome.server.controller;

import org.reactome.server.domain.TargetDigester;
import org.reactome.server.service.SearchDigesterService;
import org.reactome.server.service.TargetDigesterService;
import org.reactome.server.util.CsvWriterService;
import org.reactome.server.util.Mail;
import org.reactome.server.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Calendar.YEAR;


@SuppressWarnings("Duplicates")
@Component
@EnableScheduling
@RequestMapping("/csv")
public class CSVGeneratorScheduler {

    private final String MAIL_TEMPLATE = "csv-email-notification.ftl";
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String mailFrom;
    private final String mailTo;
    private final String mailSubject = "[Search] %s report [%s to %s]";
    private MailService mailService;
    private final String hostname;
    private final String mailHeader = "%s report [%s to %s]";

    private TargetDigesterService targetDigesterService;
    private SearchDigesterService searchDigesterService;
    private CsvWriterService csvWriterService;

    private final String folderPath;


    public CSVGeneratorScheduler(@Value("${mail.report.from}") String mailFrom,
                                 @Value("${mail.report.to}") String mailTo,
                                 @Value("${mail.report.hostname}") String hostname,
                                 @Value("${reports.folder}") String folderPath) {
        this.mailFrom = "Reactome Report <" + mailFrom + ">";
        this.mailTo = mailTo;
        this.hostname = hostname;
        this.folderPath = folderPath;
    }

    @Scheduled(cron = "0 0 12 * * SAT") // every Saturday at midday
    public void weeklyReport() {
        if (!matchesHostname()) return;
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        LocalDateTime today = LocalDateTime.now();
        Map<String, List<TargetDigester>> reportMap = new HashMap<>();
        getReports(reportMap, lastWeek, today);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(YEAR);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        File dir = new File(folderPath + "/Weekly/" + year + "/" + month);
        if (!dir.exists()) dir.mkdirs();

        List<File> targetCSVFiles = csvWriterService.writeTargetToCSV(dir, lastWeek, today, reportMap);
        List<File> searchCSVFiles = csvWriterService.writeSearchToCSV(dir, lastWeek, today, reportMap);

        String reportPath = String.format("Weekly/%d/%s", year, month);
        String subject = String.format(mailSubject, "Weekly", lastWeek.format(ofPattern(DATE_FORMAT)), today.format(ofPattern(DATE_FORMAT)));
        sendNotificationEmail("Weekly", subject, reportPath, lastWeek, today, targetCSVFiles, searchCSVFiles);
    }

    @Scheduled(cron = "0 0 1 1 * *") // every day 1 at 01AM
    public void monthlyReport() {
        if (!matchesHostname()) return;
        LocalDateTime firstDayOfTheMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime lastDayOfTheMonth = LocalDateTime.now().minusDays(1);
        Map<String, List<TargetDigester>> reportMap = new HashMap<>();
        getReports(reportMap, firstDayOfTheMonth, lastDayOfTheMonth);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(YEAR);
        File dir = new File(folderPath + "/Monthly/" + year);
        if (!dir.exists()) dir.mkdirs();

        List<File> targetCSVFiles = csvWriterService.writeTargetToCSV(dir, firstDayOfTheMonth, lastDayOfTheMonth, reportMap);
        List<File> searchCSVFiles = csvWriterService.writeSearchToCSV(dir, firstDayOfTheMonth, lastDayOfTheMonth, reportMap);

        String reportPath = String.format("Monthly/%d", year);
        String subject = String.format(mailSubject, "Monthly", firstDayOfTheMonth.format(ofPattern(DATE_FORMAT)), lastDayOfTheMonth.format(ofPattern(DATE_FORMAT)));
        sendNotificationEmail("Monthly", subject, reportPath, firstDayOfTheMonth, lastDayOfTheMonth, targetCSVFiles, searchCSVFiles);
    }

    @Scheduled(cron = " 0 0 1 1 Jan,Apr,Jul,Oct *") // every day 1 at 01AM in Jan, Apr,Jul, Oct
    public void quarterlyReport() {
        if (!matchesHostname()) return;
        LocalDateTime firstDayOfQuarter = LocalDateTime.now().minusMonths(3);
        LocalDateTime lastDayOfQuarter = LocalDateTime.now().minusDays(1);
        Map<String, List<TargetDigester>> reportMap = new HashMap<>();
        getReports(reportMap, firstDayOfQuarter, lastDayOfQuarter);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(YEAR);
        File dir = new File(folderPath + "/Quarterly/" + year);
        if (!dir.exists()) dir.mkdirs();

        List<File> targetCSVFiles = csvWriterService.writeTargetToCSV(dir, firstDayOfQuarter, lastDayOfQuarter, reportMap);
        List<File> searchCSVFiles = csvWriterService.writeSearchToCSV(dir, firstDayOfQuarter, lastDayOfQuarter, reportMap);

        String reportPath = String.format("Quarterly/%d", year);
        String subject = String.format(mailSubject, "Quarterly", firstDayOfQuarter.format(ofPattern(DATE_FORMAT)), lastDayOfQuarter.format(ofPattern(DATE_FORMAT)));
        sendNotificationEmail("Quarterly", subject, reportPath, firstDayOfQuarter, lastDayOfQuarter, targetCSVFiles, searchCSVFiles);
    }

    @Scheduled(cron = "0 0 1 1 Jan *") // every day 1 at 01AM in January
    public void yearlyReport() {
        if (!matchesHostname()) return;
        LocalDateTime firstDayOfLastYear = LocalDateTime.now().minusYears(1);
        LocalDateTime lastDayOfLastYear = LocalDateTime.now().minusDays(1);
        Map<String, List<TargetDigester>> reportMap = new HashMap<>();
        getReports(reportMap, firstDayOfLastYear, lastDayOfLastYear);
        File dir = new File(folderPath + "/Yearly/");
        if (!dir.exists()) dir.mkdirs();
        List<File> targetCSVFiles = csvWriterService.writeTargetToCSV(dir, firstDayOfLastYear, lastDayOfLastYear, reportMap);
        List<File> searchCSVFiles = csvWriterService.writeSearchToCSV(dir, firstDayOfLastYear, lastDayOfLastYear, reportMap);

        String subject = String.format(mailSubject, "Yearly", firstDayOfLastYear.format(ofPattern(DATE_FORMAT)), lastDayOfLastYear.format(ofPattern(DATE_FORMAT)));
        sendNotificationEmail("Yearly", subject, "Yearly", firstDayOfLastYear, lastDayOfLastYear, targetCSVFiles, searchCSVFiles);
    }

    /**
     * Example url - http://localhost:8080/report/csv/custom/johndoe@ebi.ac.uk?from=20200504_1200&to=20200511_1200&label=Weekly
     *
     * @param email    to whom you want to send it
     * @param fromDate format yyyyMMdd_HHmm: 20200504_1200
     * @param toDate   format yyyyMMdd_HHmm: 20200511_1200
     * @param label    Free text to appear in the subject, yearly, quarterly, monthly,weekly
     */
    @GetMapping(value = "/custom/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void customReportWithEmail(@PathVariable(name = "email") String email,
                                      @RequestParam(name = "from") String fromDate,
                                      @RequestParam(name = "to") String toDate,
                                      @RequestParam(name = "label") String label) {
        DateTimeFormatter formatter = ofPattern("yyyyMMdd_HHmm");
        LocalDateTime fromLDT = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime toLDT = LocalDateTime.parse(toDate, formatter);
        Map<String, List<TargetDigester>> reportMap = new HashMap<>();
        getReports(reportMap, fromLDT, toLDT);
        File dir = new File(folderPath + "/Custom/");
        if (!dir.exists()) dir.mkdirs();

        List<File> targetCSVFiles = csvWriterService.writeTargetToCSV(dir, fromLDT, toLDT, reportMap);
        List<File> searchCSVFiles = csvWriterService.writeSearchToCSV(dir, fromLDT, toLDT, reportMap);

        Mail mail = new Mail(mailFrom, email, "[Search] " + label + " report", MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("mailHeader", String.format(mailHeader, label, fromLDT.format(ofPattern(DATE_FORMAT)), toLDT.format(ofPattern(DATE_FORMAT))));
        model.put("label", label);
        model.put("targetFiles", targetCSVFiles);
        model.put("searchFiles", searchCSVFiles);
        model.put("reportPath", "Custom");
        mail.setModel(model);
        mailService.sendEmail(mail);
    }


    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
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
    public void setCsvWriterService(CsvWriterService csvWriterService) {
        this.csvWriterService = csvWriterService;
    }

    /**
     * hostname has the server name from where we want to send the report.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean matchesHostname() {
        try {
            return hostname.equalsIgnoreCase(InetAddress.getLocalHost().getHostName());
        } catch (Throwable t) {
            return false;
        }
    }

    private void getReports(Map<String, List<TargetDigester>> reportMap, LocalDateTime from, LocalDateTime to) {
        List<TargetDigester> targetSummary = targetDigesterService.findTargetsByDates(from, to);
        List<TargetDigester> targetRelevantSummary = new ArrayList<>();
        List<TargetDigester> targetSingleUsersSummary = new ArrayList<>();

        for (TargetDigester targetDigester : targetSummary) {
            if (targetDigester.getUniqueIPs() == 1) {
                targetSingleUsersSummary.add(targetDigester);
            } else {
                targetRelevantSummary.add(targetDigester);
            }
        }

        List<TargetDigester> searchSummary = searchDigesterService.findSearchesByDates(from, to);
        List<TargetDigester> searchRelevantSummary = new ArrayList<>();
        List<TargetDigester> searchSingleUsersSummary = new ArrayList<>();

        for (TargetDigester targetDigester : searchSummary) {
            if (targetDigester.getUniqueIPs() == 1) {
                searchSingleUsersSummary.add(targetDigester);
            } else {
                searchRelevantSummary.add(targetDigester);
            }
        }
        reportMap.put("targetRelevantSummary", targetRelevantSummary);
        reportMap.put("targetSingleSummary", targetSingleUsersSummary);
        reportMap.put("searchRelevantSummary", searchRelevantSummary);
        reportMap.put("searchSingleSummary", searchSingleUsersSummary);
    }

    private void sendNotificationEmail(String label, String subject, String reportPath, LocalDateTime fromDate, LocalDateTime toDate, List<File> targetFiles, List<File> searchFiles) {
        Mail mail = new Mail(mailFrom, mailTo, subject, MAIL_TEMPLATE);
        Map<String, Object> model = new HashMap<>();
        model.put("label", label);
        model.put("mailHeader", String.format(mailHeader, label, fromDate.format(ofPattern(DATE_FORMAT)), toDate.format(ofPattern(DATE_FORMAT))));
        model.put("reportPath", reportPath);
        model.put("targetFiles", targetFiles);
        model.put("searchFiles", searchFiles);
        mail.setModel(model);
        mailService.sendEmail(mail);
    }
}

