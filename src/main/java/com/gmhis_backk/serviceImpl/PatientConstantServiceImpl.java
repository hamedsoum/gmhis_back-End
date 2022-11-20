package com.gmhis_backk.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.PatientConstant;
import com.gmhis_backk.repository.PatientConstantRepository;
import com.gmhis_backk.service.PatientConstantService;


/**
 * 
 * @author Pascal
 *
 */
@Service
public class PatientConstantServiceImpl implements PatientConstantService {

	@Autowired
	private PatientConstantRepository repo;

	@Override
	@Transactional
	public PatientConstant save(PatientConstant patientConstant) {
		return repo.save(patientConstant);
	}

	@Override
	public PatientConstant findById(Long id) {
		return (null != id) ? repo.findById(id).orElse(null): null;
	}

	@Override
	public List<PatientConstant> findAll() {
		return repo.findAll();
	}

	@Override
	public Page<PatientConstant> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<PatientConstant> findPatientConstant(Long patientId) {
		return repo.findPatientConstant(patientId);
	}

	@Override
	public Page<PatientConstant> findPatientConstant(Long patientId, Pageable pageable) {
		return repo.findPatientConstant(patientId, pageable);
	}
	
	@Override
	public Page<PatientConstant> findPatientConstantByDate(Long patientId, Date date1, Date date2, Pageable pageable){
		return repo.findPatientConstantByDate(patientId, date1, date2, pageable);
	}

	@Override
	public List<PatientConstant> findPatientConstantByDate(Long patientId, String takeAt) throws ParseException{
		System.out.print(takeAt);
		return repo.findPatientConstantByDate(patientId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(takeAt));
	}

	@Override
	public Long findPatientConstantsNumber(Long patientId) {
		return repo.findPatientConstantsNumber(patientId);
	}

}
