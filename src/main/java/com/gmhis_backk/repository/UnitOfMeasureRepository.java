package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.UnitOfMeasure;

@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
    UnitOfMeasure findByName(String name);
	
	@Query(value = "SELECT u FROM UnitOfMeasure u WHERE active=1")
	List<UnitOfMeasure> findAllActive();
	
	@Query(value = "SELECT a FROM UnitOfMeasure a")
	Page<UnitOfMeasure>findAllUnitOfMeasure(Pageable pageable);
	
	@Query("SELECT u FROM UnitOfMeasure u WHERE u.active =:active AND u.name like %:name%")
	Page<UnitOfMeasure> findAllUnitOfMeasureByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT u FROM UnitOfMeasure u WHERE u.name like %:name%")
	Page<UnitOfMeasure> findAllUnitOfMeasureByName(String name, Pageable pageable);
	
	@Query("SELECT u FROM UnitOfMeasure u Where u.id =:id")
	UnitOfMeasure getUnitOfMeasureDetails(Integer id);
	
	@Query("SELECT u FROM UnitOfMeasure u")
	List<UnitOfMeasure>findUnitOfMeasureSimpleList();
	
	@Query(value = "SELECT u FROM UnitOfMeasure u where id= :id")
	Optional<UnitOfMeasure> findById(Long id);
}
