package com.gmhis_backk.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AdmisionHasActDTO;
import com.gmhis_backk.dto.AdmissionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AdmissionRepository;
import com.gmhis_backk.repository.PracticianRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.PatientService;
import com.gmhis_backk.service.PracticianService;
import com.gmhis_backk.service.ServiceService;



/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class AdmissionServiceImpl implements AdmissionService{


	@Autowired
	private ActService actService;

	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private AdmissionRepository repo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private PracticianRepository practicianRepo;
	
	
	@Autowired
	private PracticianService practicianService;
	
	protected User getCurrentUserId() {
		return this.userRepo.findUserByUsername(AppUtils.getUsername());
	}

	
	@Override @Transactional
	public Admission saveAdmission(AdmissionDTO admissionDto)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Patient patient = patientService.findById(admissionDto.getPatient());
		
		if (patient == null) {
			throw new ResourceNotFoundByIdException("aucun patient trouvé pour l'identifiant " );
		}
//		
		com.gmhis_backk.domain.Service service = serviceService.findServiceById(admissionDto.getService());
		if (service == null) {
			throw new ResourceNotFoundByIdException("aucune service trouvé pour l'identifiant " );
		}
//		
	    Act act = actService.findActById(admissionDto.getAct()).orElse(null);
		
		if (act == null) {
			throw new ResourceNotFoundByIdException("aucun act trouvé pour l'identifiant " );
		}
//		
		Pratician praticien = practicianService.findPracticianById(admissionDto.getPractician()).orElse(null);
			
			if (praticien == null) {
				throw new ResourceNotFoundByIdException("le practicien n'existe pas en base " );
			}
			
			
		System.out.print(praticien.getId());
		Admission admission = new Admission();
		
		BeanUtils.copyProperties(admissionDto, admission, "id");
		admission.setAdmissionNumber(getAdmissionNumber());
		admission.setPatient(patient);
		admission.setAct(act);
		admission.setPractician(praticien);
		admission.setService(service);
		admission.setAdmissionStartDate(new Date());
        admission.setAdmissionStatus("R");
        admission.setCreatedAt(new Date());
		admission.setCreatedBy(getCurrentUserId().getId());
		return repo.save(admission);
		
	}
	
	
	
	@Override
	public Admission updateAdmission(Long id, AdmissionDTO a)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{

		
		return null;
	}

	@Override
	public Admission findAdmissionByPatient(Long patient){
		return null;
	}
	
	@Override
	public Optional<Admission> findAdmissionById(Long id){
		return repo.findById(id);
	}
	
	@Override
	public Admission findLastAdmission() {
		return repo.findLastAdmission();
	}
	
	@Override
	public void addActToAdmission(AdmisionHasActDTO admissionHasActDto, int actCost, Bill bill) {
		repo.addActToAdmission(admissionHasActDto.getAdmission(), admissionHasActDto.getAct(), admissionHasActDto.getPratician(), actCost, bill.getId(), getCurrentUserId().getId());	
	}
		
	
	@Override
	public void removeAdmissionAct(Admission admission) {
		
		repo.removeAdmissionAct(admission.getId(), admission.getAct().getId());
	}
	
	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public Page<Admission> findAdmissionsByPatientName (String firstName, String lastName, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByPatientName(firstName, lastName, admissionStatus, pageable);
	}

	@Override
    public Page<Admission> findAdmissionsByAdmissionNumber(String admissionNumber, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByAdmissionNumber(admissionNumber, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByPatientExternalId(String patientExternalId, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByPatientExternalId(patientExternalId, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByCellPhone(String cellPhone, String admissionStatus,  Pageable pageable){
		return repo.findAdmissionsByCellPhone(cellPhone, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByCnamNumber(String cnamNumber, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByCnamNumber(cnamNumber, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByIdCardNumber(String idCardNumber, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByIdCardNumber(idCardNumber, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByPractician(Long practician, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByPractician(practician, admissionStatus, pageable);
	}
		
	@Override
	public Page<Admission> findAdmissionsByAct(Long act, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByAct(act, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByService(Long service, String admissionStatus, Pageable pageable){
		return repo.findAdmissionsByService(service, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsByDate (Date fromDate, Date toDate, String admissionStatus, Pageable pageable){
		return repo.findAdmissionByDate(fromDate, toDate, admissionStatus, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissions(String admissionStatus, Pageable pageable){
		return repo.findAdmissions(admissionStatus, pageable);
	}
	
	@Override
	public void setAdmissionStatusToBilled(Long id) {
		repo.setAdmissionStatusToBilled(id);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueue(Long waitingRoom, Pageable pageable){
		return repo.findAdmissionsInQueue(waitingRoom,pageable);
	}
		
	@Override
    public Page<Admission> findAdmissionsInQueueByPatientName (String firstName, String lastName, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByPatientName( firstName,  lastName, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByAdmissionNumber(String admissionNumber, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByAdmissionNumber( admissionNumber, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByPatientExternalId(String patientExternalId, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByPatientExternalId(patientExternalId, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByCellPhone(String cellPhone, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByCellPhone(cellPhone, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByCnamNumber(String cnamNumber, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByCnamNumber(cnamNumber, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByIdCardNumber(String idCardNumber, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByIdCardNumber(idCardNumber, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByPractician(Long practician, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByPractician(practician, waitingRoom, pageable);
	}
		
	@Override
	public Page<Admission> findAdmissionsInQueueByAct(Long act, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByAct(act, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByService(Long service, Long waitingRoom, Pageable pageable){	
		return repo.findAdmissionsInQueueByService(service, waitingRoom, pageable);
	}
	
	@Override
	public Page<Admission> findAdmissionsInQueueByDate (Date fromDate, Date toDate, Long waitingRoom, Pageable pageable){
		return repo.findAdmissionInQueueByDate(fromDate, toDate, waitingRoom, pageable);
	}


	@Override
	public String getAdmissionNumber() {
		
		Admission lAdmission = repo.findLastAdmission();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lAdmisionYearandMonth = "";
		String lAdmissionNb = "";
		int  number= 0;
		
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



}
