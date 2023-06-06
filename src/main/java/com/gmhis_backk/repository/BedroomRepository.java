package com.gmhis_backk.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Bedroom;

@Repository
public interface BedroomRepository extends JpaRepository<Bedroom, Long> {
	
	@Query("SELECT b FROM Bedroom b WHERE b.libelle=:libelle AND b.storey.id=:storeyId")
	Bedroom findByLibelleAndStorey(String libelle, Long storeyId);
	
	@Query("SELECT b FROM Bedroom b WHERE b.id=:id")
	Bedroom getDetail(Long id);
	
	@Query("SELECT b FROM Bedroom b WHERE b.storey.building.facilityId=:facilityId")
	List<Bedroom>listByFacility(String facilityId);
	
	@Query("SELECT b FROM Bedroom b WHERE b.libelle LIKE %:libelle% AND b.storey.building.facilityId=:facilityId")
	Page<Bedroom>findByLibelle(String libelle, String facilityId,Pageable pageable);
	
	@Query("SELECT b FROM Bedroom b WHERE b.libelle LIKE %:libelle% AND b.storey.building.facilityId=:facilityId AND b.storey.id=:storeyId")
	Page<Bedroom>findByStorey(String libelle, String facilityId,Long storeyId,Pageable pageable);
	
	@Query("SELECT b FROM Bedroom b WHERE b.libelle LIKE %:libelle% AND b.storey.building.facilityId=:facilityId AND b.bedroomType.id=:type")
	Page<Bedroom>findByBedroomType(String libelle, String facilityId,Long type,Pageable pageable);
	
	@Query("SELECT b FROM Bedroom b WHERE b.libelle LIKE %:libelle% AND b.storey.building.facilityId=:facilityId AND b.bedroomType.id=:type AND b.storey.id=:storeyId")
	Page<Bedroom>findByBedroomTypeAndStorey(String libelle, String facilityId,Long type,Long storeyId,Pageable pageable);
	
	@Query("SELECT b FROM Bedroom b WHERE b.storey.building.id=:buildingId")
	List<Bedroom>findByBuilding(Long buildingId);
	
	@Query("SELECT b FROM Bedroom b WHERE b.storey.id=:storeyId")
	List<Bedroom>findByStorey(Long storeyId);
	
}
