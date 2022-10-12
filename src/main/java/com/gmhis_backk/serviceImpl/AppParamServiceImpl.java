package com.gmhis_backk.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gmhis_backk.domain.AppParam;
import com.gmhis_backk.exception.domain.NotAnImageFileException;
import com.gmhis_backk.repository.AppParamRepository;
import com.gmhis_backk.service.AppParamService;
import com.gmhis_backk.service.EventLogService;

import static com.gmhis_backk.constant.FileConstant.APP_PARAM_FOLDER;
import static com.gmhis_backk.constant.FileConstant.DEFAULT_APP_PARAM_IMAGE_PATH;
import static com.gmhis_backk.constant.FileConstant.DOT;
import static com.gmhis_backk.constant.FileConstant.JPG_EXTENSION;
import static com.gmhis_backk.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 
 * @author Mathurin
 *
 */

@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class AppParamServiceImpl implements AppParamService{
	
	private EventLogService enventLogService;
	private AppParamRepository appParamRepository;
	
	
	
 @Autowired
	public AppParamServiceImpl(EventLogService enventLogService, AppParamRepository appParamRepository) {
		super();
		this.enventLogService = enventLogService;
		this.appParamRepository = appParamRepository;
	}

	@Override
	public AppParam updateAppParam(String nameCompany, String address, String email, String postalCode, String website, MultipartFile logo, String phone1, String phone2, String phone3, MultipartFile cachet, String currency, String header, String footPage) throws IOException, NotAnImageFileException {
		AppParam appParam= appParamRepository.findAll().get(0);
		appParam.setId(1);
		appParam.setAddress(address);
		appParam.setCachet(setImageUrl("cachet"));
		appParam.setCurrency(currency);
		appParam.setEmail(email);
		appParam.setFootPage(footPage);
		appParam.setHeader(header);
		appParam.setLogo(setImageUrl("logo"));
		appParam.setNameCompany(nameCompany);
		appParam.setPhone1(phone1);
		appParam.setPhone2(phone2);
		appParam.setPhone3(phone3);
		appParam.setPostalCode(postalCode);
		appParam.setWebsite(website);
		AppParam newAppParam = appParamRepository.save(appParam);
		saveImage(newAppParam, "logo", logo);
		saveImage(newAppParam, "cachet", cachet);
		enventLogService.addEvent("Mise a jour des parametres de : "+newAppParam.getNameCompany() ,newAppParam.getClass().getSimpleName());
		return newAppParam;
	}

	@Override
	public AppParam detail() {
		return appParamRepository.findAll().get(0);
	}
	
	
	 private void saveImage(AppParam param,String name, MultipartFile image) throws IOException, NotAnImageFileException {
	        if (image != null) {
	            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(image.getContentType())) {
	                throw new NotAnImageFileException(image.getOriginalFilename() + NOT_AN_IMAGE_FILE);
	            }
	            Path paramFolder = Paths.get(APP_PARAM_FOLDER).toAbsolutePath().normalize();
	            
	            if(!Files.exists(paramFolder)) {
	                Files.createDirectories(paramFolder);
	            }
	            Files.deleteIfExists(Paths.get(paramFolder + DOT + JPG_EXTENSION));
	            Files.copy(image.getInputStream(), paramFolder.resolve(name + DOT + JPG_EXTENSION), REPLACE_EXISTING);
	        }
	 }
	 
	 private String setImageUrl(String name) {
	        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_APP_PARAM_IMAGE_PATH + name + DOT + JPG_EXTENSION).toUriString();
	    }

}
