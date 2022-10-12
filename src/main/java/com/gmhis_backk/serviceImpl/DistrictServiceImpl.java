package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.District;
import com.gmhis_backk.repository.DistrictRepository;
import com.gmhis_backk.service.DistrictService;

/**
 * 
 * @author pascal
 *
 */
@Transactional
@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository repo;

	@Override
	public District saveDistrict(District d) {
		return repo.save(d);
	}

	@Override
	public void deleteDistrict(Long districtId) {
		repo.deleteById(districtId);
	}

	@Override
	public District findDistrictById(Long districtId) {
		return repo.getOne(districtId);
	}

	@Override
	public Page<District> findDistrictLike(String like, Pageable page) {
		return repo.findByNameContainingIgnoreCase(like, page);
	}

	@Override
	public List<District> findDistrictLike(String like) {
		return repo.findByNameContainingIgnoreCase(like);
	}

	@Override
	public Page<District> findAllDistricts(Pageable page) {
		return repo.findAll(page);
	}

	@Override
	public List<District> findAllDistricts() {
		return repo.findAll();
	}

	@Override 
	public List<District> findAllDistricts(Long regionId) {
		return repo.findDistrictsByRegionId(regionId);
	}

	@Override
	public Page<District> findAllDistricts(Long regionId, Pageable page) {
		return repo.findDistrictsByRegionId(regionId, page);
	}

	@Override
	public Page<District> findDistrictLike(String like, Long regionId, Pageable page) {
		return repo.findDistrictsByNameContainingIgnoreCaseAndRegionId(like, regionId,page);
	}


}
