package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Region;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	@Modifying
	@Query(value = "delete from region where id = :id", nativeQuery = true)
	public void deleteRegion(@Param("id") Long id);

	@Query(name = "select r from Region r join r.country co where co.id = :id")
	public List<Region> findRegionsByCountryId(@Param("id") Long countryId);
	
	@Query(name = "select r from Region r join r.country co where co.id = :id")
	public Page<Region> findRegionsByCountryId(@Param("id") Long countryId,Pageable page);

	public List<Region> findByNameContainingIgnoreCase(String name);

	public Page<Region> findByNameContainingIgnoreCase(String name, Pageable page);
	
	@Query(name = "select r from Region r join r.country co where co.id = :id and r.name like %:name%")
	public Page<Region> findRegionsByNameContainingIgnoreCaseAndCountryId(@Param("name") String name,@Param("id") Long countryId, Pageable page);

}
