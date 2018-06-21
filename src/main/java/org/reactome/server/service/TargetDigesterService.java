package org.reactome.server.service;

import org.reactome.server.domain.TargetDigester;
import org.reactome.server.repository.TargetDigesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TargetDigesterService {

    private final TargetDigesterRepository targetDigesterRepository;

    @Autowired
    public TargetDigesterService(TargetDigesterRepository targetDigesterRepository) {
        this.targetDigesterRepository = targetDigesterRepository;
    }

    public List<TargetDigester> findTargets(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return targetDigesterRepository.findByDateTerm(date);
    }

}
