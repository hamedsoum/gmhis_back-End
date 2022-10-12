package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.District;


/**
 * 
 * @author Pascal
 *
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{

	@Modifying
	@Query(value = "delete from district where id = :id", nativeQuery = true)
	public void deleteDistrict(@Param("id") Long id);

	@Query(name = "select d from District d join d.region r where r.id = :id")
	public List<District> findDistrictsByRegionId(@Param("id") Long regionId);
	
	@Query(name = "select d from District d join d.region r where r.id = :id")
	public Page<District> findDistrictsByRegionId(@Param("id") Long regionId, Pageable page);

	public List<District> findByNameContainingIgnoreCase(String name);

	public Page<District> findByNameContainingIgnoreCase(String name, Pageable page);
	
	@Query(name = "select d from District d join d.region r where r.id = :id and c.name like %:name%")
	public Page<District> findDistrictsByNameContainingIgnoreCaseAndRegionId(@Param("name") String name,@Param("id") Long regionId, Pageable page);

}
