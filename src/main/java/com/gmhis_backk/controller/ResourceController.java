package com.gmhis_backk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Resource;
import com.gmhis_backk.service.ResourceService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Mathurin
 *
 */

@RestController
@RequestMapping("/resource")
public class ResourceController {
	
    @Autowired
	ResourceService resourceService;
    
	@ApiOperation(value = "Liste des resources actives du progitiel")
	@GetMapping("/list")
	public List<Resource> listResource(){
		return resourceService.findAll();
	}
}
