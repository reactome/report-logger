package org.reactome.server.controller;

import org.reactome.server.domain.OrcidClaim;
import org.reactome.server.service.OrcidClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcid")
public class OrcidClaimController {

    private OrcidClaimService orcidClaimService;

    public OrcidClaimController() {
    }

    @PostMapping(value = "/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody List<OrcidClaim> orcidClaims) {
        orcidClaimService.saveAll(orcidClaims);
    }

    @GetMapping(value = "/load/{orcid}")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<OrcidClaim> loadByOrcid(@PathVariable String orcid) {
        return orcidClaimService.load(orcid);
    }

    @Autowired
    public void setOrcidClaimService(OrcidClaimService orcidClaimService) {
        this.orcidClaimService = orcidClaimService;
    }
}
