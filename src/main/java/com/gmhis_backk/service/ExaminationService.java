package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Examination;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface ExaminationService {

	public Examination saveExamination(Examination e);
	
	public Optional<Examination> findExaminationById(Long id);
	
	public Page<Examination> findPatientExaminations(Long patient, Pageable pageable);
	
	public void addPathologyToExamination(Long pathology, Examination examination);
	
	public void addSymptomToExamination (Long symtom, Examination examination);
	
	public void removeExaminationAllPathologies(Long id);
	
	public Long findPatientExaminationsNumber(Long patientId);

}
