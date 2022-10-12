package com.gmhis_backk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Resource;

/**
 * 
 * @author adjara
 *
 */
@Service
public interface ResourceService {
  
	List<Resource> findAll();
}
