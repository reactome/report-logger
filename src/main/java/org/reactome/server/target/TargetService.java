package org.reactome.server.target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Collection;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TargetService {
	
	private final TargetRepository targetRepository;

	@Autowired
	public TargetService(TargetRepository targetRepository) {
		this.targetRepository = targetRepository;
	}

	@Transactional
	public Target save(Target target) {
		return targetRepository.save(target);
	}

	@Transactional
	public Collection<Target> saveAll(Collection<Target> targets) {
		return targetRepository.saveAll(targets);
	}

}
