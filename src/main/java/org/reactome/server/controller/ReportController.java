package org.reactome.server.controller;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.reactome.server.target.Target;
import org.reactome.server.target.TargetService;
import org.reactome.server.target.UserAgentService;
import org.reactome.server.target.UserAgentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/report")
public class ReportController {

    private TargetService targetService;
    private UserAgentService userAgentService;

    private UserAgentStringParser parser;

    public ReportController() {
        parser = UADetectorServiceFactory.getResourceModuleParser();
    }

    @PostMapping(value = "/targets", consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public void saveAll(@RequestBody String terms,
                        @RequestParam(required = false) String ip,
                        @RequestParam(required = false) String agent,
                        @RequestParam Integer releaseNumber) {

        UserAgentType uat = getUserAgentType(agent);
        Collection<Target> targets = new HashSet<>();
        for (String term : terms.split(",|;|\\n|\\t")) {
            targets.add(new Target(term, ip, agent, releaseNumber, uat));
        }
        targetService.saveAll(targets);
    }

    private UserAgentType getUserAgentType(String agent) {
        ReadableUserAgent rua = parser.parse(agent);
        String type = rua.getType().getName();
        if (StringUtils.isEmpty(type)) {
            type = "UNKNOWN";
        }
        UserAgentType uat = userAgentService.findByName(type);
        if (uat == null) uat = userAgentService.save(type);
        return uat;
    }

    @Autowired
    public void setTargetService(TargetService targetService) {
        this.targetService = targetService;
    }

    @Autowired
    public void setUserAgentService(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }
}
