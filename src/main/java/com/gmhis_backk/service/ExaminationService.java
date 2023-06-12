package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Examination;

import javassist.NotFoundException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface ExaminationService {
	
	public Long dayNumberBetweenAdmissionFirstExaminationAndCurrentDate (Long admissionID) throws Exception;
	
	public List<Examination> findPatientExaminationsOfLastAdmision(Long patientID);

	public Examination saveExamination(Examination e);
	
	public Optional<Examination> findExaminationById(Long id);
	
	public Page<Examination> findPatientExaminations(Long patient,Long admissionID, Pageable pageable);
	
	public void addPathologyToExamination(Long pathology, Examination examination);
	
	public void addSymptomToExamination (Long symtom, Examination examination);
	
	public void removeExaminationAllPathologies(Long id);
	
	public Long findPatientExaminationsNumberByAdmission(Long admissionID);
	
	public Page<Examination> findPatientFirstExaminationsOfAdmisions(Long patientID, Pageable pageable);
	
	public Examination findAdmissionLastExamination(Long id);
	
	public Examination insertDiagnostic(Long id, String diagnostic) throws NotFoundException;


}
