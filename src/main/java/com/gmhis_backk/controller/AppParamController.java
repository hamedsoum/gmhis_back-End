package com.gmhis_backk.controller;

import static com.gmhis_backk.constant.FileConstant.APP_PARAM_FOLDER;
import static com.gmhis_backk.constant.FileConstant.FORWARD_SLASH;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gmhis_backk.domain.AppParam;
import com.gmhis_backk.exception.domain.NotAnImageFileException;
import com.gmhis_backk.service.AppParamService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Mathurin
 *
 */

@RestController
@RequestMapping("/application-parameter")
public class AppParamController {
	
	@Autowired
	AppParamService appService;
	
	@ApiOperation("modifier un paramètre dans le systeme")
	@PostMapping("/update")
	public ResponseEntity<AppParam>  addAppParam(
			@RequestParam("nameCompany") String nameCompany,
			@RequestParam("address") String address,
			@RequestParam("email") String email,
			@RequestParam(value = "postalCode", required = false) String postalCode,
			@RequestParam(value = "website", required = false) String website,
			@RequestParam(value = "logo", required = false) MultipartFile logo,
			@RequestParam(value = "phone1") String phone1,
            @RequestParam(value = "phone2", required = false) String phone2,
            @RequestParam(value = "phone3", required = false) String phone3,
			@RequestParam(value = "cachet", required = false) MultipartFile cachet,
            @RequestParam("currency") String currency,
            @RequestParam("header") String header,
            @RequestParam("footPage") String footPage) throws IOException, NotAnImageFileException {
		
		AppParam addApp= appService.updateAppParam(nameCompany, address, email, postalCode, website, logo, phone1, phone2, phone3, cachet, currency, header, footPage);
		return new ResponseEntity<AppParam>(addApp, HttpStatus.OK) ;
	}
	
	
	@ApiOperation("modifier un paramètre dans le systeme")
	@GetMapping("/detail")
	public ResponseEntity<AppParam> detail() {
		AppParam appParam= appService.detail();
		return new ResponseEntity<AppParam>(appParam,HttpStatus.OK);
	}
	
	@ApiOperation(value = "obtenir une image")
	@GetMapping(path = "/{fileName}", produces = IMAGE_JPEG_VALUE)
	public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws IOException {
		return Files.readAllBytes(Paths.get(APP_PARAM_FOLDER + FORWARD_SLASH + fileName));
	}
	
}
