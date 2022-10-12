package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Country;


/**
 * 
 * @author Pascal
 *
 */
@Service
@Transactional
public interface CountryService {
	public Country saveCountry(Country c);

	public void deleteCountry(Long countryId);

	public Country findCountryById(Long countryId);

	public Page<Country> findCountryLike(String like, Pageable page);

	public List<Country> findCountryLike(String like);

	public Page<Country> findAllCountries(Pageable page);

	public List<Country> findAllCountries();

}
