package com.gmhis_backk.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Region;
import com.gmhis_backk.repository.RegionRepository;
import com.gmhis_backk.service.RegionService;


/**
 * 
 * @author Hamed Soumahoro
 *
 */
@Transactional
@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository repo;

	@Override
	public Region saveRegion(Region r) {
		return repo.save(r);
	}

	@Override
	public void deleteRegion(Long regionId) {
		repo.deleteById(regionId);
	}

	@Override
	public Region findRegionById(Long regionId) {
		return repo.getOne(regionId);
	}

	@Override
	public Page<Region> findRegionLike(String like, Pageable page) {
		return repo.findByNameContainingIgnoreCase(like, page);
	}

	@Override
	public List<Region> findRegionLike(String like) {
		return repo.findByNameContainingIgnoreCase(like);
	}

	@Override
	public Page<Region> findAllRegions(Pageable page) {
		return repo.findAll(page);
	}

	@Override
	public List<Region> findAllRegions() {
		return repo.findAll();
	}

	@Override 
	public List<Region> findAllRegions(Long countryId) {
		return repo.findRegionsByCountryId(countryId);
	}

	@Override
	public Page<Region> findAllRegions(Long countryId, Pageable page) {
		return repo.findRegionsByCountryId(countryId, page);
	}

	@Override
	public Page<Region> findRegionLike(String like, Long countryId, Pageable page) {
		return repo.findRegionsByNameContainingIgnoreCaseAndCountryId(like, countryId,page);
	}


}
