package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.DrugPharmacologicalForm;


/**
 * 
 * @author Adjara
 *
 */
@Repository
public interface DrugPharmacologicalFormRepository extends JpaRepository<DrugPharmacologicalForm, Long> {

	public DrugPharmacologicalForm findByName(String name);

	@Query(value = "select d from DrugPharmacologicalForm d where active = 'Y'")
	public List<DrugPharmacologicalForm> findActiveForms();

	public Page<DrugPharmacologicalForm> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select d from DrugPharmacologicalForm d where d.name like %:name% and d.active = :active")
	public Page<DrugPharmacologicalForm> findByActive(@Param("name") String name,
			@Param("active") String active, Pageable p);
}
