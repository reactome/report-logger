package org.reactome.server.service;

import org.reactome.server.domain.AnalysisReportRecord;
import org.reactome.server.repository.AnalysisReportRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnalysisReportRecordService {

    private final AnalysisReportRecordRepository analysisReportRecordRepository;

    @Autowired
    public AnalysisReportRecordService(AnalysisReportRecordRepository analysisReportRecordRepository) {
        this.analysisReportRecordRepository = analysisReportRecordRepository;
    }

    @Transactional
    public AnalysisReportRecord save(AnalysisReportRecord analysisReportRecord) {
        return analysisReportRecordRepository.save(analysisReportRecord);
    }
}
