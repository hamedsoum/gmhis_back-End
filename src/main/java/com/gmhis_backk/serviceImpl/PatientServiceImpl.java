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
import com.gmhis_backk.domain.Examination;
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
import com.gmhis_backk.service.AdmissionService;
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

	@Autowired
	AdmissionService admissionService;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public Page<Patient> findByIdPatientNumber(String patientNumber, Pageable pageable) {
		return patientRepository.findByIPatientNumber(patientNumber, pageable);
	}
	
	@Override
	public Page<Patient> findByIdCardNumber(String idCardNumber, Pageable pageable) {
		return patientRepository.findByIdCardNumber(idCardNumber, pageable);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)

	public Patient save(PatientDTO patientdto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException,EmailExistException {

		System.out.println("countryOfResidence ===>" + patientdto.getCountryOfResidence());
		System.out.println("cityOfResidence ===>" + patientdto.getCityOfResidence());

		if(patientdto.getEmail() != null) {
			Boolean isEmailUsed =	patientRepository.findByEmail(patientdto.getEmail()).isEmpty();
			if(!isEmailUsed) throw new ResourceNotFoundByIdException("Email deja utilise");
		}
		
		if(ObjectUtils.isEmpty(patientdto.getCellPhone1()) || patientdto.getCellPhone1() == null) {
		}else {
			 Boolean isPhone1Used =	patientRepository.findByCellPhone1(patientdto.getCellPhone1()).isEmpty();
			 System.out.print("Le resultat de la recherche est : "+isPhone1Used);
			 if(!isPhone1Used) throw new ResourceNotFoundByIdException("le numero de telephone est deja utilisé");	
		}
		
		if(ObjectUtils.isEmpty(patientdto.getIdCardNumber()) || patientdto.getIdCardNumber() == null) {
			if(patientdto.getAge() > 16) {
				throw new ResourceNotFoundByIdException("le numero de la piece d'identé est requis");
			}
		}else {
			 Boolean isIdCardNumberUsed =	patientRepository.findByIdCardNumber(patientdto.getIdCardNumber()).isEmpty();
			 if(!isIdCardNumberUsed) throw new ResourceNotFoundByIdException("le numero de la piece d'identité est deja utilisé");	
		}
			 
		 Patient patient = new Patient();
		
		 
			BeanUtils.copyProperties(patientdto, patient, "id");
			if (patientdto.getInsurances().size() != 0) patient.setIsAssured(true);
		    String patientNumber = this.getPatientNumber();
		    patient.setPatientExternalId(patientNumber);
		    

			if (patientdto.getCountry() != null) {
				patient.setCountry(countryRepository.getOne(patientdto.getCountry()));
			}
			
			if (patientdto.getCityId() != null) {
				City city = cityRepository.getOne(patientdto.getCityId());
				patient.setCity(city);
			}
			
			if (patientdto.getCountryOfResidence() != null) {
				patient.setCountryOfResidence(countryRepository.getOne(patientdto.getCountryOfResidence()));
			}
			
			if (patientdto.getCityOfResidence() != null) {
				City cityOfResidence = cityRepository.getOne(patientdto.getCityOfResidence());
				patient.setCityOfResidence(cityOfResidence);
			}
		
			if(patientdto.getSolde() != null) {
				if(patientdto.getSolde() > 0) {
					patient.setSolde(patientdto.getSolde());
				}						
			}
				
			
			
			patient.setCreatedAt(new Date());
			patient.setCreatedBy(this.getCurrentUserId().getId());
				
			
			Patient newPatient = patientRepository.save(patient);
			//System.out.print(patientdto.getInsurances().size());
			if (patientdto.getInsurances().size() != 0) {
				for (InsuredDTO insuredDTO : patientdto.getInsurances()) {
					Insured insured = new Insured();
					Insurance insurance = new Insurance();
					InsuranceSuscriber iSubscriber = new InsuranceSuscriber();

					insurance = insuranceRepository.getActInsurranceDetails(insuredDTO.getInsurance());
					iSubscriber = insuranceSubScriberRepository.getInsuranceSuscriberDetails(insuredDTO.getInsuranceSuscriber());
						;

					if (insurance == null || iSubscriber == null) {
//						throw new ResourceNotFoundByIdException(
//								"L'assurance et/ou le souscripteur n'existe(nt) pas en base !");
						
						throw new ResourceNotFoundByIdException(
								"L'assurance et/ou le courtier n'existe(nt) pas en base !");
					}
					
					 Boolean isCardNumberUsed = insuredRepository.findByCardNumber(insuredDTO.getCardNumber()).isEmpty();
					 System.out.println(isCardNumberUsed);
					 if(!isCardNumberUsed) throw new ResourceNotFoundByIdException("le numero de carte de l'assurance " +insurance.getName() +  " est deja utilisé");

					insured.setCardNumber(insuredDTO.getCardNumber());
					insured.setCoverage(insuredDTO.getCoverage());
					insured.setCreatedAt(new Date());
					insured.setCreatedBy(this.getCurrentUserId().getId());
					insured.setInsurance(insurance);
					insured.setInsuranceSuscriber(iSubscriber);
//					insured.setIsPrincipalInsured(insuredDTO.getIsPrincipalInsured());
					insured.setPatient(newPatient);
//					insured.setPrincipalInsuredAffiliation(insuredDTO.getPrincipalInsuredAffiliation());
//					insured.setPrincipalInsuredContact(insuredDTO.getPrincipalInsuredContact());
//					insured.setPrincipalInsuredName(insuredDTO.getPrincipalInsuredName());
					insured.setSociety(insuredDTO.getSociety());
					insuredRepository.save(insured);
				}
			}

		return null;
	}

	@Override
	public Patient findById(Long id) {
		return patientRepository.findById(id).orElse(null);
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
	public String getLastExternalId(int prefixLength) {
		String val = patientRepository.findLastExernalId(prefixLength);
		if (StringUtils.isBlank(val)) {
			return "0";
		} else
			return val;
	}
	

	@Override
	public Page<Patient> findByFullName(String firstName, String lastName,String cellPhone1,String correspondant,String emergencyContact,String patientExternalId, Pageable pageable) {
		return patientRepository.findByFullName(firstName,lastName,cellPhone1,correspondant,emergencyContact,patientExternalId,pageable);
	}

	@Override
	public Patient update(Long id,PatientDTO patientdto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		 
		
		
			Patient updatePatient = patientRepository.findById(id).orElse(null);
			System.out.print(updatePatient.getFirstName());
		    Patient patient = new Patient();
		    
		    if (updatePatient != null) {
				BeanUtils.copyProperties(patientdto, updatePatient, "id", "cityId");
				
				if (patientdto.getInsurances().size() != 0) {
					updatePatient.setIsAssured(true);
				} else {
					updatePatient.setIsAssured(false);
				}

				if (patientdto.getCountry() != null) {
					updatePatient.setCountry(countryRepository.getOne(patientdto.getCountry()));
				}
				
				if (patientdto.getCityId() != null) {
					City city = cityRepository.getOne(patientdto.getCityId());
					updatePatient.setCity(city);
				}
				
				if (patientdto.getCountryOfResidence() != null) {
					updatePatient.setCountryOfResidence(countryRepository.getOne(patientdto.getCountryOfResidence()));
				}
				
				if (patientdto.getCityOfResidence() != null) {
					City cityOfResidence = cityRepository.getOne(patientdto.getCityOfResidence());
					updatePatient.setCityOfResidence(cityOfResidence);
				}
			
				
				updatePatient.setUpdatedAt(new Date());
				updatePatient.setUpdatedBy(this.getCurrentUserId().getId());
				
				patient = patientRepository.save(updatePatient);				
				
				if (patientdto.getInsurances().size() != 0) {
					for (InsuredDTO insuredDTO : patientdto.getInsurances()) {
						Insured insured = null;
						if(insuredDTO.getId()  == null) {
							insured = new Insured();
						} else {
							insured = insuredRepository.findById(insuredDTO.getId()).orElse(null);
						}
						Insurance insurance = new Insurance();
						InsuranceSuscriber iSubscriber = new InsuranceSuscriber();

						insurance = insuranceRepository.getActInsurranceDetails(insuredDTO.getInsurance());
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
							insured.setPatient(patient);
							insured.setSociety(insuredDTO.getSociety());
							insured.setDeleted("N");
							insured.setUpdatedAt(new Date());
							insured.setUpdatedBy(this.getCurrentUserId().getId());
							insuredRepository.save(insured);
						}
						
					}
				}
		    }
		
		return patient;
	}
	
	public String getPatientNumber() {
		
		Patient lPatient = patientRepository.findLastPatient();
		System.out.print(lPatient.getFirstName());
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

	@Override
	public Examination findLastAdmission(Long id) {
		Examination lastExam = admissionService.findLastExamination(id);
		return lastExam;
	}

}
