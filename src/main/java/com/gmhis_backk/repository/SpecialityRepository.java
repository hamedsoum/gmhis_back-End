package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
	public Speciality findByName(String name);

	@Query(value = "select s from Speciality s where active = 1")
	public List<Speciality> findActiveSpecialities();

	public Page<Speciality> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select s from Speciality s where s.name like %:name% and s.active = :active")
	public Page<Speciality> findByActive(@Param("name") String name,
			@Param("active") String active, Pageable p);
	
	
	@Query(value="select s from Speciality s where s.id = :id")
	public Speciality findByPraticianId(@Param("id") Long id);
}
