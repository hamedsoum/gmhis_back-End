package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.Drug;

public interface DrugRepository extends JpaRepository<Drug, UUID> {
public Drug findDrugByName(String name);
	

	@Query(value = "select f from Drug f where active = 1")
	public List<Drug> findActiveDrugs();

	public Page<Drug> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select f from Drug f where f.name like %:name% and f.active = :active")
	public Page<Drug> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
}
