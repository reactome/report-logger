package org.reactome.server.repository;

import org.reactome.server.domain.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Repository
public interface SearchRecordRepository extends AbstractRecordRepository<SearchRecord> {

}