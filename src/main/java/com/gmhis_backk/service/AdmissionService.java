package com.gmhis_backk.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.BillHasInsured;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.dto.AdmisionHasActDTO;
import com.gmhis_backk.dto.AdmissionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

import javassist.NotFoundException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface AdmissionService {

	public Admission saveAdmission(AdmissionDTO a)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public String getAdmissionNumber();
	
	public Admission updateAdmission(Long id, AdmissionDTO a)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Admission updatetakeCare(Long admissionID, Boolean takeCare)throws NotFoundException;


	public Admission findAdmissionByPatient(Long patient);
	
	public Optional<Admission> findAdmissionById(Long id);
	
	public Admission findLastAdmission();
	
	public void addActToAdmission(AdmisionHasActDTO admissionHasActDto, int actCost, Bill bill);
	
	public void removeAdmissionAct (Admission admission);
	
	public void deleteById(Long id);
	
	public Page<Admission> findAdmissions(String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByPatientName (String firstName, String lastName, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByAdmissionNumber(String admissionNumber, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByPatientExternalId(String patientExternalId, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByCellPhone(String cellPhone, String admissionStatus,String facilityId,  Pageable pageable);
	
	public Page<Admission> findAdmissionsByCnamNumber(String cnamNumber, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByIdCardNumber(String idCardNumber, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByPractician(Long practician, String admissionStatus,String facilityId, Pageable pageable);
		
	public Page<Admission> findAdmissionsByAct(Long act, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByService(Long service, String admissionStatus,String facilityId, Pageable pageable);
	
	public Page<Admission> findAdmissionsByDate (Date fromDate, Date toDate, String admissionStatus,String facilityId, Pageable pageable);
	
	public void setAdmissionStatusToBilled(Long id);
	
	public Page<Admission> findAdmissionsInQueue (Boolean takeCare,String facilityId, Pageable pageable) ;
		
	
	public Page<Admission> findAdmissionsInQueueByDate (Boolean takeCare,Date fromDate, Date toDate, Pageable pageable);
	
	public Page<Admission>findAdmissiondByDate(String date,String facilityId, Pageable pageable) throws ParseException ;

	public Page<Admission>findAdmissionsByFacility(String facilityId ,String admissionStatus, Pageable pageable);
	
	public Examination findLastExamination(Long id);
}
