package com.gmhis_backk.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.AdmissionHasAct;
import com.gmhis_backk.domain.ConventionHasAct;

@Repository
public interface ActRepository extends JpaRepository<Act, Long> {
	public Act findByName(String name);

	@Query(value = "select a from Act a where active = 1")
	public List<Act> findActiveActs();
	
	@Query(value = "select a from Act a where name like %:name%")
	public List<Act> findActListByName(@Param("name") String name);

	public Page<Act> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select a from Act a where a.name like %:name% and actCategory.id = :category and a.active = :active")
	public Page<Act> findByActive(@Param("name") String name,
			@Param("active") Boolean active,@Param("category") Long category, Pageable p);
	
	@Query(value = "select a from Act a where name like %:name% and actCategory.id = :category and active = 1")
	public Page<Act> findByCategory(@Param("name") String name, @Param("category") Long category, Pageable p);
	
	@Query(value = "select a from Act a where name like %:name% and actGroup.id = :group and active = 'Y'")
	public List<Act> findByGroup(@Param("name") String name, @Param("group") Long group);
	
	
	@Query(value = "select a from Act a where name like %:name% and  actGroup.id = :group and actCategory.id = :category and active = 'Y'")
	public List<Act> findByCriteria(@Param("name") String name, @Param("group") Long group, @Param("category") Long category);
	
	@Modifying
	@Query(value = "delete from check_up_has_act c where c.check_up_id = :id", nativeQuery = true)
	public void removeAllActs(@Param("id") Long checkUpId);

	@Modifying
	@Query(value = "delete from check_up_has_act  c where c.check_up_id = :check_up_id and c.act_id = :act_id", nativeQuery = true)
	public void removeAct(@Param("act_id") Long actId, @Param("check_up_id") Long checUpId);
	
	@Query(value = "select a from ConventionHasAct a where a.convention.id = :convention and a.act.id = :act")
	public ConventionHasAct findActByConventionAndAct(Long convention, Long act);
	
	@Query(value = "Select a from AdmissionHasAct a where a.bill.id = :bill")
	public List<AdmissionHasAct> findActsByBill(Long bill);
}
