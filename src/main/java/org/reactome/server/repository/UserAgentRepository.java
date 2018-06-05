package org.reactome.server.repository;

import org.reactome.server.domain.UserAgentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Repository
public interface UserAgentRepository extends JpaRepository<UserAgentType, Long> {

    UserAgentType findByName(String name);
}