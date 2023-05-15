package com.gmhis_backk.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.BedroomType;

@Repository
public interface BedroomTypeRepository extends JpaRepository<BedroomType, Long> {
	
	@Query("SELECT b FROM BedroomType b WHERE b.libelle=:libelle AND b.facilityId=:facilityId")
	BedroomType findByLibelleAndFacility(String libelle, String facilityId);
	
	@Query("SELECT b FROM BedroomType b WHERE b.id=:id")
	BedroomType getDetail(Long id);
	
	@Query("SELECT b FROM BedroomType b WHERE b.facilityId=:facilityId")
	List<BedroomType>listByFacility(String facilityId);
	
	@Query("SELECT b FROM BedroomType b WHERE b.libelle LIKE %:libelle% AND b.facilityId=:facilityId")
	Page<BedroomType>  findByLibelle(String libelle, String facilityId,Pageable pageable);
	
}
