package com.gmhis_backk.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Files;
import com.gmhis_backk.repository.FileDbRepository;
import com.gmhis_backk.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	FileDbRepository imageDbRepository;
	
	@Override @Transactional
	public Files SaveImge(Files dbImage) {
		// TODO Auto-generated method stub
		return imageDbRepository.save(dbImage);
	}

}
