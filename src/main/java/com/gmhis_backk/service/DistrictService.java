package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.District;


/**
 * 
 * @author Pascal
 *
 */
@Service
@Transactional
public interface DistrictService {

	public District saveDistrict(District d);

	public void deleteDistrict(Long distrcitId);

	public District findDistrictById(Long distrcitId);

	public Page<District> findDistrictLike(String like, Pageable page);
	
	public Page<District> findDistrictLike(String like, Long regionId,Pageable page);

	public List<District> findDistrictLike(String like);

	public Page<District> findAllDistricts(Pageable page);

	public List<District> findAllDistricts();

	public List<District> findAllDistricts(Long countryId);

	public Page<District> findAllDistricts(Long countryId, Pageable page);
	

}
