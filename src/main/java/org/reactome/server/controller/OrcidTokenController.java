package org.reactome.server.controller;

import org.reactome.server.domain.OrcidToken;
import org.reactome.server.service.OrcidTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orcid/token")
public class OrcidTokenController {

    private OrcidTokenService orcidTokenService;

    public OrcidTokenController() {
    }

    @PostMapping(value = "/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody OrcidToken orcidToken) {
        orcidTokenService.save(orcidToken);
    }

    @GetMapping(value = "/load/{orcid}")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    OrcidToken findByOrcid(@PathVariable String orcid) {
        return orcidTokenService.findByOrcid(orcid);
    }

    @GetMapping(value = "/revoke/{orcid}")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Boolean revoke(@PathVariable String orcid) {
        return orcidTokenService.revoke(orcid);
    }

    @Autowired
    public void setOrcidTokenService(OrcidTokenService orcidTokenService) {
        this.orcidTokenService = orcidTokenService;
    }
}
