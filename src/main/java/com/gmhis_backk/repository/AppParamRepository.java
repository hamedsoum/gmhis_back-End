package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.AppParam;

/**
 * 
 * @author Mathurin
 *
 */
@Repository
public interface AppParamRepository extends JpaRepository<AppParam, Integer>{
}
