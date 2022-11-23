package com.gmhis_backk.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gmhis_backk.domain.Image;
import com.gmhis_backk.repository.ImageDbRepository;
import com.gmhis_backk.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageDbRepository imageDbRepository;
	
	@Autowired
	ImageService imageService;
	
	@PostMapping("/add") 
	Long uploadImage(@RequestParam MultipartFile multipartFile) throws IOException {
		Image dbImage = new Image();
		
		dbImage.setName(multipartFile.getName());
//		dbImage.setContent(multipartFile.getBytes());
		
		return imageService.SaveImge(dbImage)
				.getId();
	}
	
	@GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	ByteArrayResource downloadImage(@PathVariable Long imageId) {
//	    byte[] image = imageDbRepository.findById(imageId)
//	      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
//	      .getContent();
//
//	    return new ByteArrayResource(image);
		return null;
	}
}
