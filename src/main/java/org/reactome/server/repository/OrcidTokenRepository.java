package org.reactome.server.repository;

import org.reactome.server.domain.OrcidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Repository
public interface OrcidTokenRepository extends JpaRepository<OrcidToken, Long> {

    OrcidToken findByOrcid(String orcid);
    boolean deleteByOrcid(String orcid);
}