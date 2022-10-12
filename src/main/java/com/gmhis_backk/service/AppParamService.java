package com.gmhis_backk.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gmhis_backk.domain.AppParam;
import com.gmhis_backk.exception.domain.NotAnImageFileException;

/**
 * 
 * @author Mathurin
 *
 */

@Service
public interface AppParamService {

	public AppParam updateAppParam(String nameCompany, String address, String email, String postalCode, String website, MultipartFile logo, String phone1, String phone2, String phone3, MultipartFile cachet, String currency, String header, String footPage) throws IOException, NotAnImageFileException;
	
	 AppParam detail();
}
