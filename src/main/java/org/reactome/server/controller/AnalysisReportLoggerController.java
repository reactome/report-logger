package org.reactome.server.controller;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.reactome.server.domain.AnalysisReportRecord;
import org.reactome.server.domain.UserAgentType;
import org.reactome.server.service.AnalysisReportRecordService;
import org.reactome.server.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analysis/pdf")
public class AnalysisReportLoggerController {

    private AnalysisReportRecordService analysisReportRecordService;
    private UserAgentService userAgentService;
    private final UserAgentStringParser parser;

    public AnalysisReportLoggerController() {
        parser = UADetectorServiceFactory.getResourceModuleParser();
    }

    @GetMapping(value = "/waiting")
    @ResponseStatus(value = HttpStatus.OK)
    public void targets(@RequestParam(required = false) String ip,
                        @RequestParam(required = false) Long waitingTime,
                        @RequestParam(required = false) Long reportTime,
                        @RequestParam(required = false) Integer pages,
                        @RequestParam(required = false) String agent) {
        UserAgentType uat = getUserAgentType(agent);
        AnalysisReportRecord arr = new AnalysisReportRecord(ip, waitingTime, reportTime, pages, agent, uat);
        analysisReportRecordService.save(arr);
    }

    @SuppressWarnings("Duplicates")
    private UserAgentType getUserAgentType(String agent) {
        ReadableUserAgent rua = parser.parse(agent);
        String type = rua.getType().getName();
        if (type.isEmpty()) {
            type = "UNKNOWN";
        }
        UserAgentType uat = userAgentService.findByName(type);
        if (uat == null) uat = userAgentService.save(type);
        return uat;
    }

    @Autowired
    public void setUserAgentService(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }

    @Autowired
    public void setAnalysisReportRecordService(AnalysisReportRecordService analysisReportRecordService) {
        this.analysisReportRecordService = analysisReportRecordService;
    }
}
