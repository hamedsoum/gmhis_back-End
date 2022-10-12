package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Locality;


/**
 * 
 * @author Adjara
 *
 */
@Service
@Transactional
public interface LocalityService {

	public Locality saveLocality(Locality l);

	public void deleteLocality(Long localityId);

	public Locality findLocalityById(Long localityId);

	public Page<Locality> findLocalityLike(String name, Pageable page);
	
	public Page<Locality> findLocalitiesByNameAndDistrict(String name, Long districtId,Pageable page);

	public List<Locality> findLocalityByName(String name);
	
	public Page<Locality> findLocalityByName(String name, Pageable page);

	public Page<Locality> findAllLocalities(Pageable page);

	public List<Locality> findAllLocalities();

	public List<Locality> findAllLocalitiesByDistrict(Long districtId);

	public Page<Locality> findAllLocalitiesByDistrict(Long districtId, Pageable page);
	
	public Page<Locality> findLocalitiesByNameAndCity(String name, Long cityId,Pageable page);
	
	public List<Locality> findAllLocalitiesByCity(Long cityId);

	public Page<Locality> findAllLocalitiesByCity(Long cityId, Pageable page);
}
