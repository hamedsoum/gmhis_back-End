package com.gmhis_backk.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import com.gmhis_backk.service.FileLocationService;

@RestController
@RequestMapping("file-system")
public class FileSystemImageController {

	
	  @Autowired
	    FileLocationService fileLocationService;

	    @PostMapping("/image")
	    UUID uploadImage(
	    		@RequestParam String name,
	    		@RequestParam MultipartFile image
	    		) throws Exception {
	    	System.out.println(image.getContentType());
	        return fileLocationService.save(image.getBytes(), image.getOriginalFilename(), image.getContentType());
	    }

	    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	    FileSystemResource downloadImage(@PathVariable UUID imageId) throws Exception {
	        return fileLocationService.find(imageId);
	    }
	    

	    @RequestMapping(value = "/sid", method = RequestMethod.GET,
	            produces = MediaType.IMAGE_JPEG_VALUE)

	    public ResponseEntity<Map<String, Object>>  getImage() throws IOException {
			Map<String, Object> response = new HashMap<>();

	    	var imgFile = new FileSystemResource(Paths.get("/Users/mamadouhamedsoumahoro/Documents/workspace-spring-tool/ryc_back/target/classes/images/1669215547973-Ajouter des lignes dans le corps du texte.jpg"));
	        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
	        String encodedString = Base64.getEncoder().encodeToString(bytes);
	        String basse64 = "data:image/jpg;base64," + encodedString ;
	        System.out.print(basse64);
			response.put("logo", "base64");

			return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}
