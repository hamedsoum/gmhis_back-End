package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ExaminationSymptom;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface ExaminationSymptomRepository extends JpaRepository<ExaminationSymptom, Long>{

}
