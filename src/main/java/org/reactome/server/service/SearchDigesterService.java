package org.reactome.server.service;

import org.reactome.server.domain.TargetDigester;
import org.reactome.server.repository.SearchDigesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SearchDigesterService {

    private final SearchDigesterRepository searchDigesterRepository;

    @Autowired
    public SearchDigesterService(SearchDigesterRepository searchDigesterRepository) {
        this.searchDigesterRepository = searchDigesterRepository;
    }

    public List<TargetDigester> findSearches(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTerm(date);
    }

    public List<TargetDigester> findSearchesByDates(LocalDateTime fromLDT, LocalDateTime toLDT) {
        Date fromDate = Date.from(fromLDT.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLDT.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByFromAndToDate(fromDate, toDate);
    }
}
