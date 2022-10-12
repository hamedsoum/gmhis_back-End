package com.gmhis_backk.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.service.CityService;


@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityService cityService;
	


	
}
