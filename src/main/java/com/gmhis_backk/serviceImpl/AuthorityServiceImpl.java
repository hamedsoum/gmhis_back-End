package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.repository.AuthorityRepository;
import com.gmhis_backk.service.AuthorityService;

/**
 * 
 * @author adjara
 *
 */
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	AuthorityRepository authorityRepo;
	
	@Override
	public List<String> findAuthoritiesByUser(Long userId) {
		
		return authorityRepo.findAuthoritiesByUser(userId);
	}

	@Override
	public List<Object> findAuthoritiesByids(List<Integer> ids) {
		
		return authorityRepo.findAuthoritiesByids(ids);
	}

	@Override
	public List<Integer> findAuthorityIdsByNames(String names) {
		
		String[] splitedNames = names.split(",");
		return authorityRepo.findAuthorityIdsByNames(splitedNames);
	}

}
