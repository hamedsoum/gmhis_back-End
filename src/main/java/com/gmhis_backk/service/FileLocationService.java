package com.gmhis_backk.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gmhis_backk.domain.Files;
import com.gmhis_backk.repository.FileSystemRepository;
import com.gmhis_backk.repository.FileDbRepository;


@Service @Transactional
public class FileLocationService {

	     @Autowired
	     FileDbRepository imageDbRepository;

	    @Autowired
	   FileSystemRepository fileSystemRepository;

	    public UUID save(byte[] bytes, String imageName, String type, UUID entityiD) throws Exception {
	        String location = fileSystemRepository.save(bytes, imageName);
	        return imageDbRepository.save(new Files(null,imageName, location, type,  entityiD.toString())).getId();
	    }
	    
	    public FileSystemResource find(UUID imageId) {
	        Files image = imageDbRepository.findById(imageId)
	          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	        return fileSystemRepository.findInFileSystem(image.getLocation());
	    }

}
