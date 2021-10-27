package org.reactome.server.repository;

import org.reactome.server.domain.AbstractRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@NoRepositoryBean
interface AbstractRecordRepository<T extends AbstractRecord> extends JpaRepository<T, Long> {

}