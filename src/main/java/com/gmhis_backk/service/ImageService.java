package com.gmhis_backk.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Files;

@Service
public interface ImageService {
	
	@Transactional
	public Files SaveImge( Files dbImage);
}
