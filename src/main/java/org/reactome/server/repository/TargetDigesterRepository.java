package org.reactome.server.repository;

import org.reactome.server.domain.TargetDigester;
import org.reactome.server.domain.TargetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Repository
public interface TargetDigesterRepository extends JpaRepository<TargetRecord, Long> {

    @Query(value = "SELECT new org.reactome.server.domain.TargetDigester(td.term, count(td), count(distinct td.ip)) " +
            "FROM TargetRecord td " +
            "WHERE td.created >= :fromDate " +
            "AND   td.created <= :toDate " +
            "AND td.userAgentType.name NOT IN ('Robot','UNKNOWN') " +
            "GROUP BY td.term " +
            "ORDER BY count(distinct td.ip) desc, count(td) desc, td.term")
    List<TargetDigester> findByFromAndToDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}