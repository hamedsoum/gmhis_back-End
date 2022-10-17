package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.DrugDci;

@Repository
public interface DrugDciRepository extends JpaRepository<DrugDci, Long> {

	DrugDci findByName(String name);
	
	@Query(value = "SELECT d FROM Pathology d WHERE active=1")
	List<DrugDci> findAllActive();
	
	@Query(value = "SELECT d FROM Pathology d")
	Page<DrugDci>findAllDrugDci(Pageable pageable);
	
	@Query("SELECT d FROM  Pathology d WHERE d.active =:active AND d.name like %:name%")
	Page<DrugDci> findAllAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT d FROM Pathology d WHERE d.name like %:name%")
	Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable);
	
	@Query("SELECT d FROM Pathology d Where d.id =:id")
	DrugDci getDrugDciDetails(Integer id);
	
	@Query("SELECT d FROM Pathology d")
	List<DrugDci>findAllDrugDciSimpleList();
	
	@Query(value = "SELECT d FROM Pathology d where id= :id")
	Optional<DrugDci> findById(Long id);
	
}
