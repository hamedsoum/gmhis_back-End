package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.City;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface CityService {

	public City saveCity(City c);

	public void deleteCity(Long cityId);

	public City findCityById(Long cityId);

	public Page<City> findCityLike(String like, Pageable page);
	
	public Page<City> findCityLike(String like, Long countryId,Pageable page);

	public List<City> findCityLike(String like);

	public Page<City> findAllCities(Pageable page);

	public List<City> findAllCities();

	public List<City> findAllCities(Long countryId);

	public Page<City> findAllCities(Long countryId, Pageable page);

}
