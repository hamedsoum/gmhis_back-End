package com.gmhis_backk.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Storey;


/**
 * 
 * @author Mathurin
 *
 */
@Repository
public interface StoreyRepository extends JpaRepository<Storey, Long> {

	@Query("SELECT s FROM Storey s WHERE s.libelle=:libelle AND s.building.id=:buildingId")
	Storey storeyByLibelleAndBuilding(String libelle,Long buildingId);
	 
	@Query("SELECT s FROM Storey s WHERE s.id=:id")
	Storey getDetail(Long id);
	
	@Query("SELECT s FROM Storey s WHERE s.libelle LIKE %:libelle% AND s.building.facilityId =:facilityId")
	Page<Storey> findAll(String libelle,String facilityId,Pageable pageable);
	
	@Query("SELECT s FROM Storey s WHERE s.libelle LIKE %:libelle% AND s.building.id=:buildingId AND s.building.facilityId =:facilityId")
	Page<Storey> findByBuilding(String libelle,Long buildingId,String facilityId,Pageable pageable);
	
	@Query("SELECT s FROM Storey s WHERE s.building.id=:buildingId")
	List<Storey> findByBuilding(Long buildingId);
	
	@Query("SELECT s FROM Storey s WHERE s.building.facilityId =:facilityId")
	List<Storey> findByFacility(String facilityId);
}
