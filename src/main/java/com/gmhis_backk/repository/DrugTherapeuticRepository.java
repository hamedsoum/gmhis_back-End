package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.DrugTherapeuticClass;

@Repository
public interface DrugTherapeuticRepository extends JpaRepository<DrugTherapeuticClass, Long> {
	
	DrugTherapeuticClass findByName(String name);
	
	@Query(value = "SELECT d FROM DrugTherapeuticClass d WHERE active=1")
	List<DrugTherapeuticClass> findAllActive();
	
	@Query(value = "SELECT d FROM DrugTherapeuticClass d")
	Page<DrugTherapeuticClass>findAllDrugTherapeuticClass(Pageable pageable);
	
	@Query("SELECT d FROM  DrugTherapeuticClass d WHERE d.active =:active AND d.name like %:name%")
	Page<DrugTherapeuticClass> findAllAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT d FROM DrugTherapeuticClass d WHERE d.name like %:name%")
	Page<DrugTherapeuticClass> findAllDrugTherapeuticClassByName(String name, Pageable pageable);
	
	@Query("SELECT d FROM DrugTherapeuticClass d Where d.id =:id")
	DrugTherapeuticClass getDrugTherapeuticClassDetails(Integer id);
	
	@Query("SELECT d FROM DrugTherapeuticClass d")
	List<DrugTherapeuticClass>findAllDrugTherapeuticClassSimpleList();
	
	@Query(value = "SELECT d FROM DrugTherapeuticClass d where id= :id")
	Optional<DrugTherapeuticClass> findById(Long id);
}
