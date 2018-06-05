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

	public List<TargetDigester> findLastWeekTargetsByDateTermAndIp(){
		LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
		Date date = Date.from(lastWeek.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTermAndIp(date);
	}

	public List<TargetDigester> findLastWeekTargetsByDateTerm(){
		LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
		Date date = Date.from(lastWeek.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTerm(date);
	}

	public List<TargetDigester> findLastMonthTargetsByDateTermAndIp(){
		LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
		Date date = Date.from(lastMonth.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTermAndIp(date);
	}

	public List<TargetDigester> findLastMonthTargetsByDateTerm(){
		LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
		Date date = Date.from(lastMonth.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTerm(date);
	}

	public List<TargetDigester> findTargetByDateTerm(LocalDateTime localDateTime){
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTerm(date);
	}

	public List<TargetDigester> findTargetByDateTermAndIp(LocalDateTime localDateTime){
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return targetDigesterRepository.findTargetByDateTerm(date);
	}

}
