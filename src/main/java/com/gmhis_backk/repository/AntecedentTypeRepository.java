package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.AntecedantType;

public interface AntecedentTypeRepository extends JpaRepository<AntecedantType, Long> {

	AntecedantType findByName(String name);
	
	@Query(value = "SELECT a FROM AntecedantType a WHERE active=1")
	List<AntecedantType> findAllActive();
	
	@Query(value = "SELECT a FROM AntecedantType a")
	Page<AntecedantType>findAllAntecedantType(Pageable pageable);
	
	@Query("SELECT a FROM AntecedantType a WHERE a.active =:active AND a.name like %:name%")
	Page<AntecedantType> findAlAntecedantTypeByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT a FROM AntecedantType a WHERE a.name like %:name%")
	Page<AntecedantType> findAllAntecedantTypeByName(String name, Pageable pageable);
	
	@Query("SELECT a FROM AntecedantType a Where a.id =:id")
	ActGroup getAntecedantTypeDetails(Integer id);
	
	@Query("SELECT a FROM ActGroup a")
	List<AntecedantType>findAllAntecedantTypeSimpleList();
	
	@Query(value = "SELECT a FROM AntecedantType a where id= :id")
	Optional<AntecedantType> findById(Long id);
}
