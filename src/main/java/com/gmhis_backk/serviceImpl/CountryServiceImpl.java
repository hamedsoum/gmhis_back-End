package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Country;
import com.gmhis_backk.repository.CountryRepository;
import com.gmhis_backk.service.CountryService;


/**
 * 
 * @author pascal
 *
 */
@Transactional
@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repo;

	@Override
	public Country saveCountry(Country c) {
		return repo.save(c);
	}

	@Override
	public void deleteCountry(Long countryId) {
		repo.deleteCountry(countryId);
	}

	@Override
	public Country findCountryById(Long countryId) {
		return repo.getOne(countryId);
	}

	@Override
	public Page<Country> findCountryLike(String like, Pageable page) {
		return repo.findByNameContainingIgnoreCase(like, page);
	}

	@Override
	public List<Country> findCountryLike(String like) {
		return repo.findByNameContainingIgnoreCase(like);
	}

	@Override
	public Page<Country> findAllCountries(Pageable page) {
		return repo.findAll(page);
	}

	@Override
	public List<Country> findAllCountries() {
		return repo.findAll();
	}

}
