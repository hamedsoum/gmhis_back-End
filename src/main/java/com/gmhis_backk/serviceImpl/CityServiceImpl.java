package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.City;
import com.gmhis_backk.repository.CityRepository;
import com.gmhis_backk.service.CityService;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Transactional
@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityRepository repo;

	@Override
	public City saveCity(City c) {
		return repo.save(c);
	}

	@Override
	public void deleteCity(Long cityId) {
		repo.deleteById(cityId);
	}

	@Override
	public City findCityById(Long cityId) {
		return repo.getOne(cityId);
	}

	@Override
	public Page<City> findCityLike(String like, Pageable page) {
		return repo.findByNameContainingIgnoreCase(like, page);
	}

	@Override
	public List<City> findCityLike(String like) {
		return repo.findByNameContainingIgnoreCase(like);
	}

	@Override
	public Page<City> findAllCities(Pageable page) {
		return repo.findAll(page);
	}

	@Override
	public List<City> findAllCities() {
		return repo.findAll();
	}

	@Override 
	public List<City> findAllCities(Long countryId) {
		return repo.findCitiesByCountryId(countryId);
	}

	@Override
	public Page<City> findAllCities(Long countryId, Pageable page) {
		return repo.findCitiesByCountryId(countryId, page);
	}

	@Override
	public Page<City> findCityLike(String like, Long countryId, Pageable page) {
		return repo.findCitiesByNameContainingIgnoreCaseAndCountryId(like, countryId,page);
	}

}
