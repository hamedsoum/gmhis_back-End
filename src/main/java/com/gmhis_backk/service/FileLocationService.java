package com.gmhis_backk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gmhis_backk.domain.Image;
import com.gmhis_backk.repository.FileSystemRepository;
import com.gmhis_backk.repository.ImageDbRepository;


@Service @Transactional
public class FileLocationService {

	     @Autowired
	     ImageDbRepository imageDbRepository;

	    @Autowired
	   FileSystemRepository fileSystemRepository;

	    public Long save(byte[] bytes, String imageName) throws Exception {
	        String location = fileSystemRepository.save(bytes, imageName);

	        return imageDbRepository.save(new Image(null,imageName, location))
	            .getId();
	    }
	    
	    public FileSystemResource find(Long imageId) {
	        Image image = imageDbRepository.findById(imageId)
	          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	        return fileSystemRepository.findInFileSystem(image.getLocation());
	    }

}
