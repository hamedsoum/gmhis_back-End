package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.PatientConstantType;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PatientConstantTypeRepository extends JpaRepository<PatientConstantType, Long> {

	public PatientConstantType findByName(String name);

	@Query(value = "select ct from PatientConstantType ct where active = 'Y'")
	public List<PatientConstantType> findActivePatientConstantTypes();

	@Query(value = "select ct from PatientConstantType ct join ct.patientConstantDomain d where ct.active = 'Y' and d.id = :domain")
	public List<PatientConstantType> findActivePatientConstantTypesByDomain(@Param("domain") Long domain);

	public Page<PatientConstantType> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query(value = "select p from PatientConstantType p where p.name like %:name% and p.active = :active")
	public Page<PatientConstantType> findByActive(@Param("name") String name, @Param("active") Boolean active,
			Pageable p);

	@Query(value = "select p from PatientConstantType p join p.patientConstantDomain d where p.name like %:name% and d.id = :domain")
	public Page<PatientConstantType> findByDomain(@Param("domain") Long domain, @Param("name") String name, Pageable p);

	@Query(value = "select p from PatientConstantType p join p.patientConstantDomain d where p.name like %:name% and p.active = :active and d.id = :domain")
	public Page<PatientConstantType> findByDomainAndActive(@Param("domain") Long domain, @Param("name") String name,  @Param("active") Boolean active,
			Pageable p);
}
