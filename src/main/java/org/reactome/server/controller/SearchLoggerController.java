package org.reactome.server.controller;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.reactome.server.domain.SearchRecord;
import org.reactome.server.domain.TargetRecord;
import org.reactome.server.domain.TargetResource;
import org.reactome.server.domain.UserAgentType;
import org.reactome.server.service.SearchRecordService;
import org.reactome.server.service.TargetRecordService;
import org.reactome.server.service.TargetResourceService;
import org.reactome.server.service.UserAgentService;
import org.reactome.server.util.SimpleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/search")
public class SearchLoggerController {

    private TargetRecordService targetRecordService;
    private SearchRecordService searchRecordService;
    private UserAgentService userAgentService;
    private TargetResourceService targetResourceService;

    private final UserAgentStringParser parser;

    public SearchLoggerController() {
        parser = UADetectorServiceFactory.getResourceModuleParser();
    }

    @GetMapping(value = "/testme")
    @ResponseStatus(value = HttpStatus.OK)
    public String testMe() {
        return "This is a test";
    }

    /**
     * To cover cases where term hasn't been found and it is in Reactome scope
     */
    @PostMapping(value = "/targets", consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public void targets(@RequestBody SimpleRecord[] records,
                        @RequestParam(required = false) String ip,
                        @RequestParam(required = false) String agent,
                        @RequestParam Integer releaseNumber) {
        UserAgentType uat = getUserAgentType(agent);
        Collection<TargetRecord> targets = new HashSet<>();
        for (SimpleRecord record : records) {
            if (record != null && record.getTerm() != null && record.getTerm().length() >= 3) {
                TargetResource tr = getTargetResource(record.getResource());
                targets.add(new TargetRecord(record.getTerm(), ip, agent, releaseNumber, uat, tr));
            }
        }
        targetRecordService.saveAll(targets);
    }

    /**
     * To cover cases where term hasn't been found and it is not a target.
     */
    @PostMapping(value = "/notfound", consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public void notFound(@RequestBody SimpleRecord record,
                         @RequestParam(required = false) String ip,
                         @RequestParam(required = false) String agent,
                         @RequestParam Integer releaseNumber) {
        if (record != null && record.getTerm() != null && record.getTerm().length() >= 3) {
            UserAgentType uat = getUserAgentType(agent);
            searchRecordService.save(new SearchRecord(record.getTerm(), ip, agent, releaseNumber, uat));
        }
    }

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

    private TargetResource getTargetResource(String resource) {
        TargetResource tr = targetResourceService.findByName(resource.toUpperCase());
        if (tr == null) tr = targetResourceService.save(resource.toUpperCase());
        return tr;
    }

    @Autowired
    public void setTargetRecordService(TargetRecordService targetRecordService) {
        this.targetRecordService = targetRecordService;
    }

    @Autowired
    public void setTargetResourceService(TargetResourceService targetResourceService) {
        this.targetResourceService = targetResourceService;
    }

    @Autowired
    public void setSearchRecordService(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @Autowired
    public void setUserAgentService(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }
}
