package org.reactome.server.controller;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.reactome.server.domain.EventPDF;
import org.reactome.server.domain.UserAgentType;
import org.reactome.server.service.EventPDFService;
import org.reactome.server.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/pdf")
public class EventPDFLoggerController {

    private EventPDFService eventPDFService;
    private UserAgentService userAgentService;
    private final UserAgentStringParser parser;

    public EventPDFLoggerController() {
        parser = UADetectorServiceFactory.getResourceModuleParser();
    }

    @GetMapping(value = "/waiting")
    @ResponseStatus(value = HttpStatus.OK)
    public void eventPDF(@RequestParam(required = false) String ip,
                         @RequestParam(required = false) Long waitingTime,
                         @RequestParam(required = false) Long reportTime,
                         @RequestParam(required = false) Integer pages,
                         @RequestParam(required = false) String agent) {
        UserAgentType uat = getUserAgentType(agent);
        EventPDF eventPDF = new EventPDF(ip, waitingTime, reportTime, pages, agent, uat);
        eventPDFService.save(eventPDF);
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
    public void setEventPDFService(EventPDFService eventPDFService) {
        this.eventPDFService = eventPDFService;
    }
}
