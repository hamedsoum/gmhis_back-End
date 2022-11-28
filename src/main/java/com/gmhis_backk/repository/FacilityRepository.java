package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Facility;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, UUID> {

	public Facility findByName(String name);

	@Query(value = "select f from Facility f where active = 1")
	public List<Facility> findActiveFacilities();

	public Page<Facility> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select f from Facility f where f.name like %:name% and f.active = :active")
	public Page<Facility> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
	
	@Query(value = "select f from Facility f where f.name like %:name% and f.active = :active AND f.facilityCategoryId = :facilityCategoryId")
	public Page<Facility> findFacilityByCategoryId(@Param("name") String name,
			@Param("active") Boolean active,@Param("active") String facilityCategoryId, Pageable p);
}
