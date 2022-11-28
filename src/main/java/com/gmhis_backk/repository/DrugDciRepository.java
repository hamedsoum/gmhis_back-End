package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.DrugDci;

@Repository
public interface DrugDciRepository extends JpaRepository<DrugDci, UUID> {

	DrugDci findByName(String name);
	
	@Query(value = "SELECT d FROM DrugDci d WHERE active=1")
	List<DrugDci> findAllActive();
	
	@Query(value = "SELECT d FROM DrugDci d")
	Page<DrugDci>findAllDrugDci(Pageable pageable);
	
	@Query("SELECT d FROM  DrugDci d WHERE d.active =:active AND d.name like %:name%")
	Page<DrugDci> findAllAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT d FROM DrugDci d WHERE d.name like %:name%")
	Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable);
	
	@Query("SELECT d FROM DrugDci d Where d.id =:id")
	DrugDci getDrugDciDetails(UUID id);
	
	@Query("SELECT d FROM DrugDci d")
	List<DrugDci>findAllDrugDciSimpleList();
	
	@Query(value = "SELECT d FROM DrugDci d where id= :id")
	Optional<DrugDci> findById(UUID id);
	
}
