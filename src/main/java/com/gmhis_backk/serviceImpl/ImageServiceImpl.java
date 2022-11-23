package com.gmhis_backk.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Image;
import com.gmhis_backk.repository.ImageDbRepository;
import com.gmhis_backk.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageDbRepository imageDbRepository;
	
	@Override @Transactional
	public Image SaveImge(Image dbImage) {
		// TODO Auto-generated method stub
		return imageDbRepository.save(dbImage);
	}

}
