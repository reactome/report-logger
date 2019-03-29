package org.reactome.server.repository;

import org.reactome.server.domain.OrcidClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Repository
public interface OrcidClaimRepository extends JpaRepository<OrcidClaim, Long> {

    List<OrcidClaim> findByOrcid(String orcid);

}