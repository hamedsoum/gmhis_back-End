package com.gmhis_backk.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.PatientPrescription;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PatientPrescriptionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PatientPrescriptionRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.PatientPrescriptionService;
import com.gmhis_backk.service.PracticianService;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
public class PatientPrescriptionServiceImpl implements PatientPrescriptionService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientPrescriptionRepository patientPrescriptionRepository;
	
	@Autowired
	private PracticianService practicianService;
	
	@Autowired
	private AdmissionService admissionService;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override @Transactional
	public PatientPrescription savePatientPrescription(PatientPrescriptionDTO pdto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		
System.out.println(getPatientPrescriptionNumber());		
		   Pratician pratician = practicianService.findPracticianByUser(this.getCurrentUserId().getId()).orElse(null);
			
			if (pratician == null) {
				throw new ResourceNotFoundByIdException("le practicien n'existe pas en base " );
			}		
			
			 Admission admission = admissionService.findAdmissionById(pdto.getAdmission()).orElse(null);
				
			if (admission == null) {
				throw new ResourceNotFoundByIdException("l'admission n'existe pas en base " );
			 }
				
			PatientPrescription patientPrescription = new PatientPrescription();
			patientPrescription.setPrescriptionNumber(getPatientPrescriptionNumber());
			patientPrescription.setAdmission(admission);
			patientPrescription.setPratician(pratician);
			patientPrescription.setFacility(admission.getFacility());
			patientPrescription.setDrugs(pdto.getDrugs());
			patientPrescription.setCreatedAt(new Date());
			patientPrescription.setState("N"); //N 
			return patientPrescriptionRepository.save(patientPrescription);

	}

	@Override
	public PatientPrescription updatePatientPrescription(PatientPrescriptionDTO pdto, UUID Id)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientPrescription findPatientPresciptionByName(String facility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PatientPrescription> findPatientPrescriptionById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Page<PatientPrescription> findAllPatientPrescriptions(Long patient, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PatientPrescription> findAllPrescriptions(String firstName, String lastName, String patientExternalId,
			String cellPhone, String cnamNumber, String idCardNumber, String state, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getPatientPrescriptionNumber() {
		
		PatientPrescription lPatientPrescriptionNumber = patientPrescriptionRepository.findLastPatientPrescription();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lPrescriptionYearandMonth = "";
		String lPatientPrescriptionNumberNb = "";
		int  number= 0;
		
		if(lPatientPrescriptionNumber ==  null) {
			lPrescriptionYearandMonth = year + month;
			lPatientPrescriptionNumberNb = "0000";
		}else {
			 String an = lPatientPrescriptionNumber.getPrescriptionNumber().substring(2);
			 lPrescriptionYearandMonth = an.substring(0, 4);
			 lPatientPrescriptionNumberNb = an.substring(4);	
		}
		
		if(lPrescriptionYearandMonth.equals( year + month)) {
			number = Integer.parseInt(lPatientPrescriptionNumberNb) + 1 ;
		} else {
			number = number +1;
		}
		
		return "OR" + year + month +String.format("%04d", number);
			
	}

}
