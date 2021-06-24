package org.reactome.server.repository;

import org.reactome.server.domain.OrcidDigester;
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
public interface OrcidDigesterRepository extends JpaRepository<OrcidDigester, Long> {

    @Query(value = "SELECT new org.reactome.server.domain.OrcidDigester(ot.name, oc.orcid, count(distinct oc.stId)) " +
            "FROM OrcidClaim oc, OrcidToken ot " +
            "WHERE oc.orcid = ot.orcid " +
            "GROUP BY oc.orcid " +
            "ORDER BY count(distinct oc.stId) desc, ot.name")
    List<OrcidDigester> findAllClaimed();

    @Query(value = "SELECT new org.reactome.server.domain.OrcidDigester(ot.name, oc.orcid, count(distinct oc.stId)) " +
            "FROM OrcidClaim oc, OrcidToken ot " +
            "WHERE oc.orcid = ot.orcid " +
            "AND date(oc.created) <= :date " +
            "GROUP BY oc.orcid " +
            "ORDER BY count(distinct oc.stId) desc, ot.name")
    List<OrcidDigester> findAllClaimedByDate(@Param("date") Date date);

    @Query(value = "SELECT new org.reactome.server.domain.OrcidDigester(ot.name, oc.orcid, count(distinct oc.stId)) " +
            "FROM OrcidClaim oc, OrcidToken ot " +
            "WHERE oc.orcid = ot.orcid " +
            "AND   date(oc.created) = :date " +
            "GROUP BY oc.orcid " +
            "ORDER BY count(distinct oc.stId) desc, ot.name")
    List<OrcidDigester> findBySingleDate(@Param("date") Date date);

    @Query(value = "SELECT new org.reactome.server.domain.OrcidDigester(ot.name, oc.orcid, count(distinct oc.stId)) " +
            "FROM OrcidClaim oc, OrcidToken ot " +
            "WHERE oc.orcid = ot.orcid " +
            "AND   date(oc.created) >= :fromDate " +
            "AND   date(oc.created) <= :toDate " +
            "GROUP BY oc.orcid " +
            "ORDER BY count(distinct oc.stId) desc, ot.name")
    List<OrcidDigester> findByFromAndToDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}