package com.gmhis_backk.controller;



import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Locality;
import com.gmhis_backk.service.LocalityService;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/locality")
public class LocalityController {
	
	@Autowired
	LocalityService localityService;
	

	
	@ApiOperation(value = "Liste paginée de tous toutes localites dans le systeme")
	@GetMapping(value = "/list")
	public ResponseEntity<Map<String, Object>> getPaginatedLocalities(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) Long district,
			@RequestParam(required = false) Long city,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		List<Locality> countryList = new ArrayList<Locality>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Locality> pageCountry;

		pageCountry = localityService.findAllLocalities(paging);
		
		if (StringUtils.isNotBlank(name)) {

			pageCountry = localityService.findLocalityByName(name.trim(), paging);
		}else {
			pageCountry = localityService.findAllLocalities(paging);
		}
	    countryList = pageCountry.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("items", countryList);
		response.put("currentPage", pageCountry.getNumber());
		response.put("totalItems", pageCountry.getTotalElements());
		response.put("totalPages", pageCountry.getTotalPages());
		response.put("size", pageCountry.getSize());
		response.put("first", pageCountry.isFirst());
		response.put("last", pageCountry.isLast());
		response.put("empty", pageCountry.isEmpty());
		return new ResponseEntity<>(response, OK);
			
	}
	
}
