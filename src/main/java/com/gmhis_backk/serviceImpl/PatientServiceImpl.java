package com.gmhis_backk.serviceImpl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.City;
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.InsuranceSuscriber;
import com.gmhis_backk.domain.Insured;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.InsuredDTO;
import com.gmhis_backk.dto.PatientDTO;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.CityRepository;
import com.gmhis_backk.repository.CountryRepository;
import com.gmhis_backk.repository.InsuranceSubscriberRepository;
import com.gmhis_backk.repository.InsuredRepository;
import com.gmhis_backk.repository.InsurranceRepository;
import com.gmhis_backk.repository.PatientRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientService;




/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	PatientRepository patientRepository;

	@Autowired
	private CityRepository cityRepository;


	@Autowired	
	private InsuredRepository insuredRepository;

	@Autowired	
	private InsurranceRepository insuranceRepository;

	@Autowired	
	private InsuranceSubscriberRepository insuranceSubScriberRepository;
	
	
	@Autowired	
	private CountryRepository countryRepository;
	
	@Autowired
	UserRepository userRepository;

	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override @Transactional
	public Patient save(PatientDTO patientdto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException,EmailExistException {
		if(ObjectUtils.isEmpty(patientdto.getEmail()) || patientdto.getEmail() == null) {
			throw new ResourceNotFoundByIdException("l'adresse email est requise");
		}else {
		 Boolean isEmailUsed =	patientRepository.findByEmail(patientdto.getEmail()).isEmpty();
		 if(!isEmailUsed) throw new ResourceNotFoundByIdException("Email deja utilise");
		}
		
		 Boolean isPhone1Used =	patientRepository.findByCellPhone1OrCellPhone2(patientdto.getCellPhone1(), patientdto.getCellPhone1()).isEmpty();
		 if(!isPhone1Used) throw new ResourceNotFoundByIdException("Premier numero de telephone est deja utilisé");
		 
		 Boolean isPhone2Used =	patientRepository.findByCellPhone1OrCellPhone2(patientdto.getCellPhone2(), patientdto.getCellPhone2()).isEmpty();
		 if(!isPhone2Used) throw new ResourceNotFoundByIdException("Deuxieme numero de telephone est deja utilisé");
				
		 
		 Patient patient = new Patient();
		
		 
			BeanUtils.copyProperties(patientdto, patient, "id");
			
		    String patientNumber = this.getPatientNumber();
		    patient.setPatientExternalId(patientNumber);

			if (patientdto.getCountry() != null) {
				patient.setCountry(countryRepository.getOne(patientdto.getCountry()));
			}
			
			if (patientdto.getCityId() != null) {
				City city = cityRepository.getOne(patientdto.getCityId());
				patient.setCity(city);
			}
		
			
			patient.setCreatedAt(new Date());
			patient.setCreatedBy(this.getCurrentUserId().getId());
			
			Patient newPatient = patientRepository.save(patient);
			System.out.print(patientdto.getInsurances().size());
			if (patientdto.getInsurances().size() != 0) {
				for (InsuredDTO insuredDTO : patientdto.getInsurances()) {
					Insured insured = new Insured();
					Insurance insurance = new Insurance();
					InsuranceSuscriber iSubscriber = new InsuranceSuscriber();

					insurance = insuranceRepository.getActGroupDetails(insuredDTO.getInsurance());
					iSubscriber = insuranceSubScriberRepository.getInsuranceSuscriberDetails(insuredDTO.getInsuranceSuscriber());
						;

					if (insurance == null || iSubscriber == null) {
						throw new ResourceNotFoundByIdException(
								"L'assurance et/ou le souscripteur n'existe(nt) pas en base !");
					}

					insured.setCardNumber(insuredDTO.getCardNumber());
					insured.setCoverage(insuredDTO.getCoverage());
					insured.setCreatedAt(new Date());
					insured.setCreatedBy(this.getCurrentUserId().getId());
					insured.setInsurance(insurance);
					insured.setInsuranceSuscriber(iSubscriber);
					insured.setIsPrincipalInsured(insuredDTO.getIsPrincipalInsured());
					insured.setPatient(newPatient);
					insured.setPrincipalInsuredAffiliation(insuredDTO.getPrincipalInsuredAffiliation());
					insured.setPrincipalInsuredContact(insuredDTO.getPrincipalInsuredContact());
					insured.setPrincipalInsuredName(insuredDTO.getPrincipalInsuredName());
					insuredRepository.save(insured);
				}
			}

		return newPatient;
	}

	@Override
	public Patient findById(Long id) {
		return patientRepository.findById(id).orElse(null);
	}

	@Override
	public List<Patient> findByFirstName(String s) {
		return patientRepository.findByFirstName(s);
	}

	@Override
	public List<Patient> findByLastName(String s) {
		return patientRepository.findByLastName(s);
	}

	@Override
	public List<Patient> findByPatientExternalId(String s) {
		return patientRepository.findByPatientExternalId(s);
	}

	@Override
	public List<Patient> findByCellPhone(String s) {
		return patientRepository.findByCellPhone1OrCellPhone2(s, s);
	}

	@Override
	public List<Patient> findByEmail(String s) {
		return patientRepository.findByEmail(s);
	}

	@Override
	public List<Patient> findByCnamNumber(String s) {
		return patientRepository.findByCnamNumber(s);
	}

	@Override
	public List<Patient> findByIdCardNumber(String s) {
		return patientRepository.findByIdCardNumber(s);
	}

	@Override
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	@Override
	public Page<Patient> findAll(Pageable pageable) {
		return patientRepository.findAll(pageable);
	}

	@Override
	public Page<Patient> findAllContaining(String firstName, String lastName, String patientExternalId,
			String cellPhone, String cnamNumber, String idCardNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return patientRepository.findPatients(firstName, lastName, patientExternalId, cellPhone, cnamNumber, idCardNumber, pageable);
	}

	@Override
	public String getLastExternalId(int prefixLength) {
		String val = patientRepository.findLastExernalId(prefixLength);
		if (StringUtils.isBlank(val)) {
			return "0";
		} else
			return val;
	}
	

	@Override
	public Page<Patient> findByFullName(String firstName, String lastName, Pageable pageable) {
		return patientRepository.findByFullName(firstName,lastName, pageable);
	}

	@Override
	public Page<Patient> findByFirstName(String firstName, Pageable pageable) {
		return patientRepository.findByFirstNameContainingIgnoreCase(firstName, pageable);
	}

	@Override
	public Page<Patient> findByLastName(String lastName, Pageable pageable) {
		return patientRepository.findByLastNameContainingIgnoreCase(lastName, pageable);
	}

	@Override
	public Page<Patient> findByPatientExternalId(String patientExternalId, Pageable pageable) {
		return patientRepository.findByPatientExternalIdContainingIgnoreCase(patientExternalId, pageable);
	}

	@Override
	public Page<Patient> findByCellPhone(String cellPhone, Pageable pageable) {
		return patientRepository.findByCellPhone(cellPhone, pageable);
	}

	@Override
	public Page<Patient> findByCnamNumber(String cnamNumber, Pageable pageable) {
		return patientRepository.findByCnamNumberContainingIgnoreCase(cnamNumber, pageable);
	}

	@Override
	public Page<Patient> findByIdCardNumber(String idCardNumber, Pageable pageable) {
		return patientRepository.findByIdCardNumberContainingIgnoreCase(idCardNumber, pageable);
	}

	@Override
	public Patient update(Long id,PatientDTO patientdto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
//		if(ObjectUtils.isEmpty(patientdto.getEmail()) || patientdto.getEmail() == null) {
//			throw new ResourceNotFoundByIdException("l'adresse email est requise");
//		}else {
//		 Boolean isEmailUsed =	patientRepository.findByEmail(patientdto.getEmail()).isEmpty();
//		 if(!isEmailUsed) throw new ResourceNotFoundByIdException("Email deja utilise");
//		}
//		
//		 Boolean isPhone1Used =	patientRepository.findByCellPhone1OrCellPhone2(patientdto.getCellPhone1(), patientdto.getCellPhone1()).isEmpty();
//		 if(!isPhone1Used) throw new ResourceNotFoundByIdException("Premier numero de telephone est deja utilisé");
//		 
//		 Boolean isPhone2Used =	patientRepository.findByCellPhone1OrCellPhone2(patientdto.getCellPhone2(), patientdto.getCellPhone2()).isEmpty();
//		 if(!isPhone2Used) throw new ResourceNotFoundByIdException("Deuxieme numero de telephone est deja utilisé");
//		
//		 Boolean isPatientExternalId2Used =	patientRepository.findByPatientExternalId(patientdto.getPatientExternalId()).isEmpty();
//		 if(!isPatientExternalId2Used) throw new ResourceNotFoundByIdException("Numero patient deja utilisé");
		 
			Patient updatePatient = patientRepository.findById(id).orElse(null);
			System.out.print(updatePatient.getFirstName());
		    Patient patient = new Patient();
		    
		    if (updatePatient != null) {
				BeanUtils.copyProperties(patientdto, updatePatient, "id", "cityId");
				
				if (patientdto.getCountry() != null) {
					updatePatient.setCountry(countryRepository.getOne(patientdto.getCountry()));
				}
				
				if (patientdto.getCityId() != null) {
					City city = cityRepository.getOne(patientdto.getCityId());
					updatePatient.setCity(city);
				}
			
				
				updatePatient.setCreatedAt(new Date());
				updatePatient.setCreatedBy(this.getCurrentUserId().getId());
				
				patient = patientRepository.save(updatePatient);
				System.out.print(patientdto.getInsurances().size());
				
				
				if (patientdto.getInsurances().size() != 0) {
					for (InsuredDTO insuredDTO : patientdto.getInsurances()) {
						Insured insured = null;
						if(insuredDTO.getId()  == 0) {
							insured = new Insured();
						} else {
							insured = insuredRepository.findById(insuredDTO.getId()).orElse(null);
						}
						Insurance insurance = new Insurance();
						InsuranceSuscriber iSubscriber = new InsuranceSuscriber();

						insurance = insuranceRepository.getActGroupDetails(insuredDTO.getInsurance());
						iSubscriber = insuranceSubScriberRepository.getInsuranceSuscriberDetails(insuredDTO.getInsuranceSuscriber());

						if (insurance == null || iSubscriber == null) {
							throw new ResourceNotFoundByIdException(
									"L'assurance et/ou le souscripteur n'existe(nt) pas en base !");
						}
						
						if(insured  != null){
							if(ObjectUtils.isNotEmpty(insured.getId())) insured.setId(insuredDTO.getId());
							insured.setCardNumber(insuredDTO.getCardNumber());
							insured.setCoverage(insuredDTO.getCoverage());
							insured.setCreatedAt(new Date());
							insured.setCreatedBy(this.getCurrentUserId().getId());
							insured.setInsurance(insurance);
							insured.setInsuranceSuscriber(iSubscriber);
							insured.setIsPrincipalInsured(insuredDTO.getIsPrincipalInsured());
							insured.setPatient(patient);
							insured.setPrincipalInsuredAffiliation(insuredDTO.getPrincipalInsuredAffiliation());
							insured.setPrincipalInsuredContact(insuredDTO.getPrincipalInsuredContact());
							insured.setPrincipalInsuredName(insuredDTO.getPrincipalInsuredName());
							insuredRepository.save(insured);
						}
						
					}
				}
		    }
		
		return patient;
	}
	
	public String getPatientNumber() {
		
		Patient lPatient = patientRepository.findLastPatient();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lPatientYearandMonth = "";
		String lPatientNb = "";
		int  number= 0;
		
		if(lPatient ==  null) {
			lPatientYearandMonth = year + month;
			lPatientNb = "0000000";
		}else {
			 String an = lPatient.getPatientExternalId().substring(2);
			 lPatientYearandMonth = an.substring(0, 4);
			 lPatientNb = an.substring(4);	
		}
		
		
		if(lPatientYearandMonth.equals( year + month)) {
			number = Integer.parseInt(lPatientNb) + 1 ;
		} else {
			number = number +1;
		}
		
		return "PT" + year + month +String.format("%04d", number);
			
	}

	
}
