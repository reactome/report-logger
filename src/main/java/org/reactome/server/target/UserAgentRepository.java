package org.reactome.server.target;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgentRepository extends JpaRepository<UserAgentType, Long> {

    UserAgentType findByName(String name);
}