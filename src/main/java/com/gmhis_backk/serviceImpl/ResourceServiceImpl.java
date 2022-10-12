package com.gmhis_backk.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Resource;
import com.gmhis_backk.repository.ResourceRepository;
import com.gmhis_backk.service.ResourceService;

/**
 * 
 * @author adjara
 *
 */
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	ResourceRepository resourceRepo;
	
	@Override
	public List<Resource> findAll() {
		return resourceRepo.findAll();
	}

}
