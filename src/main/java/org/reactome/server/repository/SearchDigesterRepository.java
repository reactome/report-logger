package org.reactome.server.repository;

import org.reactome.server.domain.SearchRecord;
import org.reactome.server.domain.TargetDigester;
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
@SuppressWarnings("ALL")
public interface SearchDigesterRepository extends JpaRepository<SearchRecord, Long> {

    @Query(value = "SELECT new org.reactome.server.domain.TargetDigester(td.term, count(td), count(distinct td.ip)) " +
            "FROM SearchRecord td " +
            "WHERE td.created >= :fromDate " +
            "AND   td.created <= :toDate " +
            "AND td.userAgentType.name NOT IN ('Robot','UNKNOWN') " +
            "GROUP BY td.term " +
            "ORDER BY count(distinct td.ip) desc, count(td) desc, td.term")
    List<TargetDigester> findByFromAndToDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}