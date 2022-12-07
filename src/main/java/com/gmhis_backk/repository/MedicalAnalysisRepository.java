package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.Drug;
import com.gmhis_backk.domain.MedicalAnalysisSpecilaity;
import com.gmhis_backk.domain.Prescription;

public interface MedicalAnalysisRepository extends JpaRepository<MedicalAnalysisSpecilaity, UUID> {
	
	public MedicalAnalysisSpecilaity findMedicalAnalysisSpecilaityByName(String name);

	
	public Page<MedicalAnalysisSpecilaity> findByNameContainingIgnoreCase(String name, Pageable page);

	
	@Query(value = "SELECT f FROM MedicalAnalysisSpecilaity f WHERE f.name LIKE %:name% AND f.active = :active")
	public Page<MedicalAnalysisSpecilaity> findMedicalAnalysisSpecialityByNameAndActiceAndDci(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
	
	@Query(value = "SELECT a FROM MedicalAnalysisSpecilaity a WHERE active=1 ORDER BY a.name ASC ")
	List<MedicalAnalysisSpecilaity> findAllActive();
	
}
