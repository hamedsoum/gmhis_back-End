package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.FaciityCategory;
import com.gmhis_backk.domain.FacilityType;

public interface FacilityCategoryRepository extends JpaRepository<FaciityCategory, UUID> {
	
	public FaciityCategory findByName(String name);
	

	@Query(value = "select f from FaciityCategory f where active = 1")
	public List<FaciityCategory> findActiveFacilityCategories();

	public Page<FaciityCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select f from FaciityCategory f where f.name like %:name% and f.active = :active")
	public Page<FaciityCategory> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
}
