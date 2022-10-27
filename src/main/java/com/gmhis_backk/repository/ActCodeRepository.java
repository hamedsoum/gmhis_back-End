package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.ConventionHasActCode;

@Repository
public interface ActCodeRepository extends JpaRepository<ActCode, Long> {
	ActCode findByName(String name);

	@Query(value = "SELECT a FROM ActCode a WHERE active=1")
	List<ActCode> findAllActive();
	
	@Query(value = "SELECT a FROM ActCode a")
	Page<ActCode>findAllActCode(Pageable pageable);
	
	@Query("SELECT a FROM ActCode a WHERE a.active =:active AND a.name like %:name%")
	Page<ActCode> findAllActCodeByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT a FROM ActCode a WHERE a.name like %:name%")
	Page<ActCode> findAllActCodeByName(String name, Pageable pageable);
	
	@Query("SELECT a FROM ActCode a Where a.id =:id")
	ActCode getActCodeDetails(Integer id);
	
	@Query("SELECT a FROM ActCode a")
	List<ActCode>findAllActCodeSimpleList();
	
	@Query(value = "SELECT a FROM ActCode a where id= :id")
	Optional<ActCode> findById(Long id);
	
	@Query(value = "select a from ConventionHasActCode a where a.convention.id = :convention and a.actCode.id = :actCode")
	public ConventionHasActCode findActCodeByConventionAndAct(Long convention, Long actCode);
	
}
