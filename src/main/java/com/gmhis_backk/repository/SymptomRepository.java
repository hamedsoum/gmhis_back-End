package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Symptom;


/**
 * 
 * @author Hamed soumahoro 
 *
 */
@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
	
	public Symptom findByName(String name);

	@Query(value = "select s from Symptom s where s.active = 1")
	public List<Symptom> findActives();

	public Page<Symptom> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query("SELECT p FROM Symptom p WHERE p.name like %:name%")
	Page<Symptom> findAllSymptomByName(String name, Pageable pageable);
	
	@Query(value = "select s from Symptom s where s.name like %:name% and s.active = :active")
	public Page<Symptom> findByActive(@Param("name") String name,
			@Param("active") String active, Pageable p);
	
	@Query(value = "SELECT s FROM Symptom s where id= :id")
	Optional<Symptom> findById(Long id);
}
