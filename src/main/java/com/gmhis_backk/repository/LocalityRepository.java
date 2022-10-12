package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Locality;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long>{

	@Modifying
	@Query(value = "delete from locality where id = :id", nativeQuery = true)
	public void deleteLocality(@Param("id") Long id);

	@Query(name = "select l from Locality l join l.district d where d.id = :id")
	public List<Locality> findLocalitiesByDistrictId(@Param("id") Long districtId);
	
	@Query(name = "select l from Locality l join l.city c where c.id = :id")
	public List<Locality> findLocalitiesByCityId(@Param("id") Long cityId);
	
	@Query(name = "select l from Locality l join l.district d where d.id = :id")
	public Page<Locality> findLocalitiesByDistrictId(@Param("id") Long districtId, Pageable page);
	
	@Query(name = "select l from Locality l join l.city c where c.id = :id")
	public Page<Locality> findLocalitiesByCityId(@Param("id") Long cityId, Pageable page);

	public List<Locality> findByNameContainingIgnoreCase(String name);

	public Page<Locality> findByNameContainingIgnoreCase(String name, Pageable page);
	
	@Query(name = "select l from Locality l join l.district d where d.id = :id and l.name like %:name%")
	public Page<Locality> findLocalitiesByNameContainingIgnoreCaseAndDistrictId(@Param("name") String name,@Param("id") Long districtId, Pageable page);

	@Query(name = "select l from Locality l join l.city c where c.id = :id and l.name like %:name%")
	public Page<Locality> findLocalitiesByNameContainingIgnoreCaseAndCityId(@Param("name") String name,@Param("id") Long cityId, Pageable page);

}
