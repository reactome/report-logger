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

    public List<TargetDigester> findLastWeekSearchesByDateTermAndIp() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        Date date = Date.from(lastWeek.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTermAndIp(date);
    }

    public List<TargetDigester> findLastWeekSearchesByDateTerm() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        Date date = Date.from(lastWeek.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTerm(date);
    }

    public List<TargetDigester> findLastMonthSearchesByDateTermAndIp() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Date date = Date.from(lastMonth.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTermAndIp(date);
    }

    public List<TargetDigester> findLastMonthSearchesByDateTerm() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Date date = Date.from(lastMonth.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTerm(date);
    }

    public List<TargetDigester> findSearchesByDateTerm(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTerm(date);
    }

    public List<TargetDigester> findSearchesByDateTermAndIp(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return searchDigesterRepository.findByDateTerm(date);
    }

}
