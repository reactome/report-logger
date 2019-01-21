package org.reactome.server.service;

import org.reactome.server.domain.EventPDF;
import org.reactome.server.repository.EventPDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventPDFService {

    private final EventPDFRepository eventPDFRepository;

    @Autowired
    public EventPDFService(EventPDFRepository eventPDFRepository) {
        this.eventPDFRepository = eventPDFRepository;
    }

    @Transactional
    public EventPDF save(EventPDF eventPDF) {
        return eventPDFRepository.save(eventPDF);
    }
}
