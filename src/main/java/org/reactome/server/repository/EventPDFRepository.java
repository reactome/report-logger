package org.reactome.server.repository;

import org.reactome.server.domain.EventPDF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPDFRepository extends JpaRepository<EventPDF, Long> {

}