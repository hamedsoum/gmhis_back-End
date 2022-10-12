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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.City;
import com.gmhis_backk.domain.Country;
import com.gmhis_backk.domain.Region;
import com.gmhis_backk.service.CityService;
import com.gmhis_backk.service.CountryService;
import com.gmhis_backk.service.RegionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/country")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	@Autowired 
	CityService cityService;
	
	@Autowired
	RegionService regionService;
	
	@ApiOperation(value = "Liste paginée de tous les pays dans le systeme")
	@GetMapping(value = "/list")
	public ResponseEntity<Map<String, Object>> listCountry(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {

		List<Country> countryList = new ArrayList<Country>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Country> pageCountry;

		pageCountry = countryService.findAllCountries(paging);
		
		if (StringUtils.isNotBlank(name)) {

			pageCountry = countryService.findCountryLike(name.trim(), paging);
		}else {
			pageCountry = countryService.findAllCountries(paging);
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
	
	@ApiOperation(value = "retourne la liste des noms et id des pays ")
	@GetMapping("/names")
	public ResponseEntity<List<Map<String, Object>>> getCountriesNames() {

		List<Map<String, Object>> response = new ArrayList<>();


		List<Country> countries = countryService.findAllCountries();
		countries.forEach(country -> {
			Map<String, Object> cMap = new HashMap<>();
			cMap.put("id", country.getId());
			cMap.put("name", country.getName());
			response.add(cMap);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "retourne la liste paginée des villes d'un pays a partir de l'identifiant du pays")
	@GetMapping("/p_cities/{id}")
	public ResponseEntity<Map<String, Object>> getCitiesFromCountry(@PathVariable("id") Long cId,
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		List<City> cityList = new ArrayList<City>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<City> pCities;
		if (StringUtils.isEmpty(name)) {
			pCities = cityService.findAllCitiesByCountryId(cId, paging);
		} else {
			pCities = cityService.findCityLikeAndCountry(name, cId, paging);
		}

		cityList = pCities.getContent();
		

	
		response.put("items", cityList);
		response.put("currentPage", pCities.getNumber());
		response.put("totalItems", pCities.getTotalElements());
		response.put("totalPages", pCities.getTotalPages());
		response.put("size", pCities.getSize());
		response.put("first", pCities.isFirst());
		response.put("last", pCities.isLast());
		response.put("empty", pCities.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "retourne la liste non paginée des villes d'un pays a partir de l'identifiant du pays")
	@GetMapping("/cities_name/{id}")
	public ResponseEntity<List<Map<String, Object>>> getCitiesNamesFromCountry(@PathVariable("id") Long cId) {

		List<Map<String, Object>> response = new ArrayList<>();

		List<City> cities = cityService.findAllCities(cId);
	
		cities.forEach(city -> {
			Map<String, Object> cMap = new HashMap<>();
			cMap.put("id", city.getId());
			cMap.put("name", city.getName());
			response.add(cMap);
		});

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "retourne la liste paginée des region d'un pays a partir de l'identifiant du pays")
	@GetMapping("/p_regions/{id}")
	public ResponseEntity<Map<String, Object>> getRegionCountry(@PathVariable("id") Long cId,
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		List<Region> regionList = new ArrayList<Region>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Region> pRegions;
		if (StringUtils.isEmpty(name)) {
			pRegions = regionService.findAllRegionsByCountryId(cId, paging);
		} else {
			pRegions = regionService.findRegionLikeAndCountryId(name, cId, paging);
		}

		regionList = pRegions.getContent();
		

	
		response.put("items", regionList);
		response.put("currentPage", pRegions.getNumber());
		response.put("totalItems", pRegions.getTotalElements());
		response.put("totalPages", pRegions.getTotalPages());
		response.put("size", pRegions.getSize());
		response.put("first", pRegions.isFirst());
		response.put("last", pRegions.isLast());
		response.put("empty", pRegions.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "retourne la liste non paginée des villes d'un pays a partir de l'identifiant du pays")
	@GetMapping("/regions_name/{id}")
	public ResponseEntity<List<Map<String, Object>>> getregionsNamesFromCountry(@PathVariable("id") Long cId) {

		List<Map<String, Object>> response = new ArrayList<>();

		List<Region> regions = regionService.findAllRegions(cId);
	
		regions.forEach(city -> {
			Map<String, Object> cMap = new HashMap<>();
			cMap.put("id", city.getId());
			cMap.put("name", city.getName());
			response.add(cMap);
		});

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
