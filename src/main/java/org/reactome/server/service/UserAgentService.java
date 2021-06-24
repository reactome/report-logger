package org.reactome.server.service;

import org.reactome.server.domain.UserAgentType;
import org.reactome.server.repository.UserAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserAgentService {

    private final UserAgentRepository userAgentRepository;

    @Autowired
    public UserAgentService(UserAgentRepository userAgentRepository) {
        this.userAgentRepository = userAgentRepository;
    }

    public UserAgentType findByName(String name) {
        return userAgentRepository.findByName(name);
    }

    public UserAgentType save(String name) {
        return userAgentRepository.saveAndFlush(new UserAgentType(name));
    }

}
