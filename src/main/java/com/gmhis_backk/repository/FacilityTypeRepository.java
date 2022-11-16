package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.FacilityType;

public interface FacilityTypeRepository extends JpaRepository<FacilityType, UUID> {
	
	public FacilityType findByName(String name);
	

	@Query(value = "select f from FacilityType f where active = 1")
	public List<FacilityType> findActiveFacilitiesType();

	public Page<FacilityType> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select f from FacilityType f where f.name like %:name% and f.active = :active")
	public Page<FacilityType> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
}
