package org.reactome.server.service;

import org.reactome.server.domain.OrcidClaim;
import org.reactome.server.repository.OrcidClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrcidClaimService {

    private final OrcidClaimRepository orcidClaimRepository;

    @Autowired
    public OrcidClaimService(OrcidClaimRepository orcidClaimRepository) {
        this.orcidClaimRepository = orcidClaimRepository;
    }

//    @Transactional
//    public SearchRecord save(SearchRecord searchRecord) {
//        return searchRecordRepository.save(searchRecord);
//    }

    @Transactional
    public Collection<OrcidClaim> saveAll(Collection<OrcidClaim> orcidClaims) {
        return orcidClaimRepository.saveAll(orcidClaims);
    }

    @Transactional
    public List<OrcidClaim> load(String orcid) {
        return orcidClaimRepository.findByOrcid(orcid);
    }
}
