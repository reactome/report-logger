package org.reactome.server.service;

import org.reactome.server.domain.SearchRecord;
import org.reactome.server.domain.TargetRecord;
import org.reactome.server.repository.SearchRecordRepository;
import org.reactome.server.repository.TargetRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchRecordService {

    private final SearchRecordRepository searchRecordRepository;

    @Autowired
    public SearchRecordService(SearchRecordRepository searchRecordRepository) {
        this.searchRecordRepository = searchRecordRepository;
    }

    @Transactional
    public SearchRecord save(SearchRecord searchRecord) {
        return searchRecordRepository.save(searchRecord);
    }

    @Transactional
    public Collection<SearchRecord> saveAll(Collection<SearchRecord> targets) {
        return searchRecordRepository.saveAll(targets);
    }

}
