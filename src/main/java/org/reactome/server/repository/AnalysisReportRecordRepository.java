package org.reactome.server.repository;

import org.reactome.server.domain.AnalysisReportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisReportRecordRepository extends JpaRepository<AnalysisReportRecord, Long> {

}