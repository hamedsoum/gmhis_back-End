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
	
	@Query(value = "SELECT p FROM Pathology p WHERE active=1")
	List<DrugDci> findAllActive();
	
	@Query(value = "SELECT p FROM Pathology p")
	Page<DrugDci>findAllDrugDci(Pageable pageable);
	
	@Query("SELECT p FROM  Pathology p WHERE p.active =:active AND p.name like %:name%")
	Page<DrugDci> findAllAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT p FROM Pathology p WHERE p.name like %:name%")
	Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable);
	
	@Query("SELECT p FROM Pathology p Where p.id =:id")
	DrugDci getDrugDciDetails(Integer id);
	
	@Query("SELECT a FROM Pathology a")
	List<DrugDci>findAllDrugDciSimpleList();
	
	@Query(value = "SELECT p FROM Pathology p where id= :id")
	Optional<DrugDci> findById(Long id);
	
}
