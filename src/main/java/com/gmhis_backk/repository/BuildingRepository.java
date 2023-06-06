package com.gmhis_backk.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
	
	@Query("SELECT b FROM Building b WHERE b.libelle=:libelle AND b.facilityId=:facilityId")
	Building findByLibelleAndFacility(String libelle, String facilityId);
	
	@Query("SELECT b FROM Building b WHERE b.id=:id")
	Building getDetail(Long id);
	
	@Query("SELECT b FROM Building b WHERE b.libelle LIKE %:libelle% AND b.facilityId=:facilityId")
	Page<Building>  findByLibelle(String libelle, String facilityId,Pageable pageable);
	
	@Query("SELECT b FROM Building b WHERE b.facilityId=:facilityId")
	List<Building> findByFacility(String facilityId);
	
}
