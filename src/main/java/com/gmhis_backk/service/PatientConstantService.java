package com.gmhis_backk.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.PatientConstant;



/**
 * 
 * @author Pascal
 *
 */
@Service
@Transactional
public interface PatientConstantService {

	public PatientConstant save(PatientConstant ct);

	public PatientConstant findById(Long id);
	
	public List<PatientConstant> findPatientConstant(Long patientId);
	
	public List<PatientConstant> findPatientConstantByDate(Long patientId, String takeAt) throws ParseException;


	public Page<PatientConstant> findPatientConstant(Long patientId, Pageable pageable);
	
	public List<PatientConstant> findAll();

	public Page<PatientConstant> findAll(Pageable pageable);
	
	public Page<PatientConstant> findPatientConstantByDate(Long patientId, Date date1, Date date2, Pageable pageable);

	public Long findPatientConstantsNumber(Long patientId);


}
