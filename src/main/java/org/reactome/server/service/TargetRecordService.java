package org.reactome.server.service;

import org.reactome.server.domain.TargetRecord;
import org.reactome.server.repository.TargetRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TargetRecordService {

    private final TargetRecordRepository targetRecordRepository;

    @Autowired
    public TargetRecordService(TargetRecordRepository targetRecordRepository) {
        this.targetRecordRepository = targetRecordRepository;
    }

    @Transactional
    public TargetRecord save(TargetRecord target) {
        return targetRecordRepository.save(target);
    }

    @Transactional
    public Collection<TargetRecord> saveAll(Collection<TargetRecord> targets) {
        return targetRecordRepository.saveAll(targets);
    }

}
