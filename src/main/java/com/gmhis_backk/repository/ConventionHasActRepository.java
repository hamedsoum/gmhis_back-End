package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ConventionHasAct;


/**
 * 
 * @author Adjara
 *
 */
@Repository
public interface ConventionHasActRepository extends JpaRepository<ConventionHasAct, Long>{

	@Modifying
	@Query(value = "delete from convention_has_act c where c.convention_id = :id", nativeQuery = true)
	public void removeAllActs(@Param("id") Long conventionId);

	@Modifying
	@Query(value = "delete from convention_has_act  c where c.convention_id = :convention_id and c.act_id = :act_id", nativeQuery = true)
	public void removeAct(@Param("act_id") Long actId, @Param("convention_id") Long conventionId);
}
