package org.reactome.server.service;

import org.reactome.server.domain.TargetResource;
import org.reactome.server.repository.TargetResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TargetResourceService {
    private final TargetResourceRepository targetResourceRepository;

    @Autowired
    public TargetResourceService(TargetResourceRepository targetResourceRepository) {
        this.targetResourceRepository = targetResourceRepository;
    }

    public TargetResource findByName(String name) {
        return targetResourceRepository.findByName(name);
    }

    public TargetResource save(String name) {
        return targetResourceRepository.saveAndFlush(new TargetResource(name));
    }
}
