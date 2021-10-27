package org.reactome.server.service;

import org.reactome.server.domain.OrcidDigester;
import org.reactome.server.repository.OrcidDigesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class OrcidDigesterService {

    private final OrcidDigesterRepository orcidDigesterRepository;

    @Autowired
    public OrcidDigesterService(OrcidDigesterRepository orcidDigesterRepository) {
        this.orcidDigesterRepository = orcidDigesterRepository;
    }

    public List<OrcidDigester> findAllClaimed() {
        return orcidDigesterRepository.findAllClaimed();
    }

    public List<OrcidDigester> findAllClaimedByDate(LocalDateTime date) {
        return orcidDigesterRepository.findAllClaimedByDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public List<OrcidDigester> findBySingleDate(LocalDateTime date) {
        return orcidDigesterRepository.findBySingleDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @SuppressWarnings("Duplicates")
    public List<OrcidDigester> findByDates(LocalDateTime fromLDT, LocalDateTime toLDT) {
        Date fromDate = Date.from(fromLDT.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLDT.atZone(ZoneId.systemDefault()).toInstant());
        return orcidDigesterRepository.findByFromAndToDate(fromDate, toDate);
    }
}
