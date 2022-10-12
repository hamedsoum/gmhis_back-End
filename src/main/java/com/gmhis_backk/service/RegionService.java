package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Region;


/**
 * 
 * @author Hamed Soumahoro
 *
 */
@Service
@Transactional
public interface RegionService {
	
	public Region saveRegion(Region R);

	public void deleteRegion(Long cityId);

	public Region findRegionById(Long cityId);

	public Page<Region> findRegionLike(String like, Pageable page);
	
	public Page<Region> findRegionLike(String like, Long countryId,Pageable page);

	public List<Region> findRegionLike(String like);

	public Page<Region> findAllRegions(Pageable page);

	public List<Region> findAllRegions();

	public List<Region> findAllRegions(Long countryId);

	public Page<Region> findAllRegions(Long countryId, Pageable page);
}
