package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Pathology;

@Repository
public interface PathologyRepository extends JpaRepository<Pathology, Long> {
	
	Pathology findByName(String name);
	
	@Query(value = "SELECT p FROM Pathology p WHERE active=1")
	List<Pathology> findAllActive();
	
	@Query(value = "SELECT p FROM Pathology p")
	Page<Pathology>findAllPathology(Pageable pageable);
	
	@Query("SELECT p FROM  Pathology p WHERE p.active =:active AND p.name like %:name%")
	Page<Pathology> findAllAllPathologyByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT p FROM Pathology p WHERE p.name like %:name%")
	Page<Pathology> findAllPathologyByName(String name, Pageable pageable);
	
	@Query("SELECT p FROM Pathology p Where p.id =:id")
	Pathology getPathologyDetails(Integer id);
	
	@Query("SELECT p FROM Pathology p")
	List<Pathology>findAllActPathologySimpleList();
	
	@Query(value = "SELECT p FROM Pathology p where id= :id")
	Optional<Pathology> findById(Long id);
	

}
