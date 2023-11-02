package com.gmhis_backk.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.admission.Admission;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AdmisionHasActDTO;
import com.gmhis_backk.dto.AdmissionCreate;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AdmissionRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.ExaminationService;
import com.gmhis_backk.service.PatientService;
import com.gmhis_backk.service.PracticianService;

import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
@Log4j2
public class AdmissionServiceImpl implements AdmissionService{
	
	@Autowired
	private ActService actService;

	@Autowired
	private ActCategoryService specialityService;
	
	@Autowired
	private ExaminationService examinationService;
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private AdmissionRepository repo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private PracticianService practicianService;
	
	protected User getCurrentUserId() {
		return this.userRepo.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public Admission create(AdmissionCreate admissionCreate)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		
		Pratician pratician = null;
		
		Patient patient = patientService.findById(admissionCreate.getPatient());
		if (patient == null) throw new ResourceNotFoundByIdException("Aucun patient trouvé pour l'identifiant. " );

        ActCategory speciality = specialityService.findById(admissionCreate.getSpeciality()).orElse(null);
        if(speciality == null) throw new ResourceNotFoundByIdException("Aucune Specialité trouvée pour l'identifiant. " );
		
	    Act act = actService.findActById(admissionCreate.getAct()).orElse(null);
		if (act == null) throw new ResourceNotFoundByIdException("Aucun act trouvé pour l'identifiant. " );
	
		if (admissionCreate.getPractician() != null) pratician = practicianService.findPracticianById(admissionCreate.getPractician()).orElse(null);
			
		Admission admission = new Admission();
	
		BeanUtils.copyProperties(admissionCreate, admission, "id");
		admission.setTakeCare(false);
		admission.setAdmissionNumber(getAdmissionNumber());
		admission.setPatient(patient);
		admission.setAct(act);
		admission.setPractician(pratician);
		admission.setSpeciality(speciality);
		admission.setAdmissionStartDate(new Date());
        admission.setAdmissionStatus("R");
        admission.setFacilityId(this.getCurrentUserId().getFacilityId());;
        admission.setCreatedAt(admissionCreate.getCreatedAt());
		admission.setCreatedBy(getCurrentUserId().getId());
		return repo.save(admission);
		
	}
	
	
	
	@Override
	public Admission update(Long id, AdmissionCreate aDto)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Admission updateAdmission = repo.findById(id).orElse(null);

		Patient patient = patientService.findById(aDto.getPatient());
		if (patient == null) throw new ResourceNotFoundByIdException("Aucun patient trouvé pour l'identifiant. " );

        ActCategory speciality = specialityService.findById(aDto.getSpeciality()).orElse(null);
        if(speciality == null) throw new ResourceNotFoundByIdException("Aucun Specialite trouvée pour l'identifiant. " );
		
	    Act act = actService.findActById(aDto.getAct()).orElse(null);
		if (act == null) throw new ResourceNotFoundByIdException("Aucun act trouvé pour l'identifiant. " );
	
		Pratician pratician = practicianService.findPracticianById(aDto.getPractician()).orElse(null);
		if (pratician == null) throw new ResourceNotFoundByIdException("Aucun Practicien trouvé pour l'identifiant. " );

		if (updateAdmission == null) throw new ResourceNotFoundByIdException("Aucune admission trouvée pour l'identifiant"); 
		
		BeanUtils.copyProperties(aDto, updateAdmission, "id");
		updateAdmission.setPatient(patient);
		updateAdmission.setAct(act);
		updateAdmission.setPractician(pratician);
		updateAdmission.setSpeciality(speciality);
		updateAdmission.setTakeCare(false);
		updateAdmission.setUpdatedAt(new Date());
		updateAdmission.setUpdatedBy(getCurrentUserId().getId());
		return repo.save(updateAdmission);
	}

	@Override
	public Admission findAdmissionByPatient(Long patient){
		return null;
	}
	
	@Override
	public Optional<Admission> retrieve(Long id){
		return repo.findById(id);
	}
	
	@Override
	public Admission findLastAdmission() {
		return repo.findLastAdmission();
	}
	
	@Override
	public Examination findLastExamination(Long id) {
		return examinationService.findAdmissionLastExamination(id);
	}
	
	@Override
	public void addActToAdmission(AdmisionHasActDTO admissionHasActDto, int actCost, Bill bill) {
			if(admissionHasActDto.getPratician() == 0) repo.addActToAdmission(admissionHasActDto.getAdmission(), admissionHasActDto.getAct(),null, actCost, bill.getId(), getCurrentUserId().getId());	
			else repo.addActToAdmission(admissionHasActDto.getAdmission(), admissionHasActDto.getAct(),admissionHasActDto.getPratician(), actCost, bill.getId(), getCurrentUserId().getId());	

	}
		
	@Override
	public void removeAdmissionAct(Admission admission) {
		repo.removeAdmissionAct(admission.getId(), admission.getAct().getId());
	}
	
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public Page<Admission> findByPatientName(String firstName, String lastName, String admissionStatus, String facilityId, Pageable pageable){
		return repo.findAdmissionsByPatientName(firstName, lastName, admissionStatus ,facilityId, pageable);
	}

	@Override
    public Page<Admission> findAdmissionsByAdmissionNumber(String admissionNumber, String admissionStatus, String facilityId, Pageable pageable){
		return repo.findAdmissionsByAdmissionNumber(admissionNumber, admissionStatus, facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByPatientExternalId(String patientExternalId, String admissionStatus, String facilityId, Pageable pageable){
		return repo.findAdmissionsByPatientExternalId(patientExternalId, admissionStatus, facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByCellPhone(String cellPhone, String admissionStatus, String facilityId,  Pageable pageable){
		return repo.findAdmissionsByCellPhone(cellPhone, admissionStatus, facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByCnamNumber(String cnamNumber, String admissionStatus, String facilityId, Pageable pageable){
		return repo.findAdmissionsByCnamNumber(cnamNumber, admissionStatus, facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByIdCardNumber(String idCardNumber, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findAdmissionsByIdCardNumber(idCardNumber, admissionStatus, facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByPractician(Long practician, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findAdmissionsByPractician(practician, admissionStatus,facilityId, pageable);
	}
		
	@Override
	public Page<Admission> findAdmissionsByAct(Long act, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findAdmissionsByAct(act, admissionStatus,facilityId, pageable);
	}


	public Page<Admission> findByType(String type, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findByType(type, admissionStatus,facilityId, pageable);
	}
	@Override
	public Page<Admission> findAdmissionsByService(Long service, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findAdmissionsByService(service, admissionStatus,facilityId, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByDate (Date fromDate, Date toDate, String admissionStatus,String facilityId, Pageable pageable){
		return repo.findAdmissionByDate(fromDate, toDate, admissionStatus,facilityId, pageable);
	}
	
	@Override
	public void setAdmissionStatusToBilled(Long id) {
		repo.setAdmissionStatusToBilled(id);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueue(Boolean takeCare, String facilityId, Pageable pageable){
		Pratician practican = practicianService.findPracticianByUser(getCurrentUserId().getId()).orElse(null);
		
		if (practican == null) return repo.findAllAdmissionsInQueue(takeCare,facilityId,pageable);
			
		System.out.println(practican.getActCategory().getId());
		return repo.findAdmissionsInQueue(takeCare,facilityId,practican.getActCategory().getId(),pageable);
	}
		
	@Override
	public Page<Admission> findAdmissionsInQueueByDate (Boolean takeCare,Date fromDate, Date toDate, Pageable pageable){
		return repo.findAdmissionInQueueByDate(takeCare,fromDate, toDate, pageable);
	}

	@Override
	public String getAdmissionNumber() {
		
		Admission lAdmission = repo.findLastAdmission();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lAdmisionYearandMonth = "";
		String lAdmissionNb = "";
		int number= 0;
		
		if(lAdmission ==  null) {
			lAdmisionYearandMonth = year + month;
			lAdmissionNb = "0000";
		}else {
			 String an = lAdmission.getAdmissionNumber().substring(2);
			 lAdmisionYearandMonth = an.substring(0, 4);
			 lAdmissionNb = an.substring(4);	
		}
		
		if(lAdmisionYearandMonth.equals( year + month)) {
			number = Integer.parseInt(lAdmissionNb) + 1 ;
		} else {
			number = number +1;
		}
		
		return "AD" + year + month +String.format("%04d", number);		
	}

	@Override
	public Page<Admission> findAdmissiondByDate(String date,String facilityId, Pageable pageable) throws ParseException {
		String[] dates = date.split(","); 
		return repo.findByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"),facilityId, pageable);
	}
	@Override
	public Page<Admission> findAdmissionsByFacility(String facilityId, String admissionStatus, Pageable pageable) {
		return repo.findAdmissionsByFacility( admissionStatus, facilityId, pageable);
	}

	@Override
	public Admission updateTakeCare(Long admissionID, Boolean takeCare) throws NotFoundException {
		Admission admission = repo.findById(admissionID).orElse(null);
		if(admission == null) throw new NotFoundException("Admission non trouvée");

		admission.setTakeCare(takeCare);
		admission.setTakeCareAt(new Date());
		admission.setTakeCareBy(getCurrentUserId().getId());
		repo.save(admission);
		return admission;
	}
	
	@Override
	public Admission supervitory(Long admissionID) throws NotFoundException {
		
		Admission admission = repo.findById(admissionID).orElse(null);
		if(admission == null) throw new NotFoundException("Admission non trouvée");

		if(admission.getSupervisoryNumber() == 6) throw new NotFoundException("Vous avez atteint le nombre de consultation de surveillance possible ! Veuillez demander une nouvelle Consultation");

		admission.setSupervisoryNumber(admission.getSupervisoryNumber() + 1);
		admission.setSupervisoryLastDate(new Date());
		repo.save(admission);
		admission.setTakeCare(false);
		return admission;
	}


}
