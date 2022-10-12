package com.gmhis_backk.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 
 * @author adjara
 *
 */
@Service
public interface AuthorityService {
	
	List<String> findAuthoritiesByUser(Long userId);
	
	List<Object> findAuthoritiesByids(List<Integer> ids);
	
	List<Integer> findAuthorityIdsByNames(String names);
}
