package com.gmhis_backk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import com.gmhis_backk.service.FileLocationService;

@RestController
@RequestMapping("file-system")
public class FileSystemImageController {

	
	  @Autowired
	    FileLocationService fileLocationService;

	    @PostMapping("/image")
	    Long uploadImage(
	    		@RequestParam String name,
	    		@RequestParam MultipartFile image
	    		) throws Exception {
	    	System.out.println(name);
	        return fileLocationService.save(image.getBytes(), image.getOriginalFilename());
	    }

	    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	    FileSystemResource downloadImage(@PathVariable Long imageId) throws Exception {
	        return fileLocationService.find(imageId);
	    }
}
