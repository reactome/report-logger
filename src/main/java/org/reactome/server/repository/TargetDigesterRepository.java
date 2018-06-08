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

@SuppressWarnings("ALL")
@Repository
public interface TargetDigesterRepository extends JpaRepository<TargetRecord, Long> {

    @Query(value = "SELECT new org.reactome.server.domain.TargetDigester(td.term, td.ip, count(td)) " +
            "FROM TargetRecord td " +
            "WHERE td.created >= :date " +
            "GROUP BY td.term, td.ip " +
            "ORDER BY count(td) desc, td.term")
    List<TargetDigester> findByDateTermAndIp(@Param("date")Date date);

    @Query(value = "SELECT new org.reactome.server.domain.TargetDigester(td.term, count(td)) " +
            "FROM TargetRecord td " +
            "WHERE td.created >= :date " +
            "GROUP BY td.term " +
            "ORDER BY count(td) desc, td.term")
    List<TargetDigester> findByDateTerm(@Param("date")Date date);
}