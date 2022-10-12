package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Locality;
import com.gmhis_backk.repository.LocalityRepository;
import com.gmhis_backk.service.LocalityService;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	private LocalityRepository repo; 
	
	@Override
	public Locality saveLocality(Locality l) {
		return repo.save(l);
	};

	@Override
	public void deleteLocality(Long localityId){
		repo.deleteById(localityId);
	};

	@Override
	public Locality findLocalityById(Long localityId){
		return repo.getOne(localityId);
	};

	@Override
	public Page<Locality> findLocalityLike(String name, Pageable page){
		return repo.findByNameContainingIgnoreCase(name, page);
	};
	
	@Override
	public Page<Locality> findLocalitiesByNameAndDistrict(String name, Long districtId,Pageable page){
		return repo.findLocalitiesByNameContainingIgnoreCaseAndDistrictId(name, districtId, page);
	};

	@Override
	public List<Locality> findLocalityByName(String name){
		return repo.findByNameContainingIgnoreCase(name);
	};

	
	@Override
	public Page<Locality> findLocalityByName(String name, Pageable page){
		return repo.findByNameContainingIgnoreCase(name, page);
	};

	
	@Override
	public Page<Locality> findAllLocalities(Pageable page){
		return repo.findAll(page);
	};

	@Override
	public List<Locality> findAllLocalities(){
		return repo.findAll();
	};

	@Override
	public List<Locality> findAllLocalitiesByDistrict(Long districtId){
		return repo.findLocalitiesByDistrictId(districtId);
	};

	@Override
	public Page<Locality> findAllLocalitiesByDistrict(Long districtId, Pageable page){
		return repo.findLocalitiesByDistrictId(districtId, page);
	};
	
	@Override
	public Page<Locality> findLocalitiesByNameAndCity(String name, Long cityId,Pageable page){
		return repo.findLocalitiesByNameContainingIgnoreCaseAndCityId(name, cityId, page);
	};
	
	@Override
	public List<Locality> findAllLocalitiesByCity(Long cityId){
		return repo.findLocalitiesByCityId(cityId);
	};

	@Override
	public Page<Locality> findAllLocalitiesByCity(Long cityId, Pageable page){
		return repo.findLocalitiesByCityId(cityId, page);
	};
}
