package org.reactome.server.service;

import org.reactome.server.domain.OrcidToken;
import org.reactome.server.repository.OrcidTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrcidTokenService {

    private final OrcidTokenRepository orcidTokenRepository;

    @Autowired
    public OrcidTokenService(OrcidTokenRepository orcidTokenRepository) {
        this.orcidTokenRepository = orcidTokenRepository;
    }

    public OrcidToken save(OrcidToken orcidToken) {
        return orcidTokenRepository.save(orcidToken);
    }

    public OrcidToken findByOrcid(String orcid) {
        return orcidTokenRepository.findByOrcid(orcid);
    }

    public boolean revoke(String orcid) {
        return orcidTokenRepository.deleteByOrcid(orcid);
    }
}
