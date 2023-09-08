package com.gmhis_backk.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.ExaminationHasPathology;
import com.gmhis_backk.domain.ExaminationSymptom;
import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.Symptom;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.repository.ExaminationHasPathologyRepository;
import com.gmhis_backk.repository.ExaminationRepository;
import com.gmhis_backk.repository.ExaminationSymptomRepository;
import com.gmhis_backk.repository.PathologyRepository;
import com.gmhis_backk.repository.PracticianRepository;
import com.gmhis_backk.repository.SymptomRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.BillService;
import com.gmhis_backk.service.ExaminationService;

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
public class ExaminationServiceImpl implements ExaminationService{

	@Autowired
	private ExaminationRepository repo;
	
	@Autowired
	private PathologyRepository pathologyRepo;
	
	@Autowired
	private SymptomRepository symptomRepo;
	
	@Autowired
	private ExaminationSymptomRepository examSymptomRepo;
	
	@Autowired
	private ExaminationHasPathologyRepository examPathologyRepo;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PracticianRepository practicianRepository;

	@Autowired
	private BillService billService;
	
	
	protected List<Map<String, Object>> map(List<Examination> examinations) {
		List<Map<String, Object>> examinationList = new ArrayList<>();
		
		examinations.stream().forEach(examination -> {
			
			billService.findBillByAdmissionId(null);
			Map<String, Object> billMap = new HashMap<>();
			billMap.put("date", examination.getStartDate());
			billMap.put("practicianName", examination.getPratician().getNom() + " "+ examination.getPratician().getPrenoms());
			billMap.put("patientNumber", examination.getAdmission().getPatient().getPatientExternalId());
			billMap.put("patientName", examination.getAdmission().getPatient().getFirstName() + " " +  examination.getAdmission().getPatient().getLastName());
			billMap.put("amount", examination.getAdmission().getAct().getActCode().getValue() *  examination.getAdmission().getAct().getCoefficient());

			examinationList.add(billMap);
		});
		return examinationList;
	}
	
		public ResponseEntity<Map<String, Object>>  searchPracticianExaminations(Map<String, ?> cashierSearch) throws NotFoundException {
		
		Map<String, Object> response = new HashMap<>();
		
		int page = (int) cashierSearch.get("page");
		String[] sort = (String[]) cashierSearch.get("sort");
		int size = (int) cashierSearch.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Examination> pexaminations = null;

		log.info("practicianID ===> {}",  getCurrentUser().getId());
		
		Pratician practician = practicianRepository.findByUser(getCurrentUser().getId()).orElse(null);
		if(practician == null) throw new NotFoundException("Practician non trouvée");
			
		else {
			Long practicianID = practician.getId();
			pexaminations = repo.retrievePracticianExaminations(practicianID, pageable);
		} 
		
		List<Examination> examinations = pexaminations.getContent();
		
		log.info("examinations size {}", examinations.size());

		List<Map<String, Object>> examination = this.map(examinations);
		response.put("items", examination);
		response.put("totalElements", pexaminations.getTotalElements());
		response.put("totalPages", pexaminations.getTotalPages());
		response.put("size", pexaminations.getSize());
		response.put("pageNumber", pexaminations.getNumber());
		response.put("numberOfElements", pexaminations.getNumberOfElements());
		response.put("first", pexaminations.isFirst());
		response.put("last", pexaminations.isLast());
		response.put("empty", pexaminations.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@Override
	public Examination saveExamination(Examination a) {
		return repo.save(a);
	}
	
	@Override
	public Optional<Examination> findExaminationById(Long id) {
		return repo.findById(id);
	}
	
	@Override
	public Page<Examination> findPatientExaminations(Long patient, Long admissionID, Pageable pageable){
		return repo.findPatientExaminations(patient,admissionID, pageable);
	}
	
	@Override
	public void addPathologyToExamination(Long pathology, Examination examination) {
	
		Pathology p = pathologyRepo.findById(pathology).orElse(null);
		if(p != null) {	
			ExaminationHasPathology examPatholy = new ExaminationHasPathology();
			examPatholy.setCreatedAt(new Date());
			examPatholy.setExamination(examination);
			examPatholy.setPathology(p);
			examPatholy.setCreatedBy(this.getCurrentUser().getId());
			examPathologyRepo.save(examPatholy);
			repo.save(examination);
		}
	}
	
	@Override @Transactional
	public void addSymptomToExamination(Long symptom, Examination examination) {
	
		Symptom s = symptomRepo.findById(symptom).orElse(null);
		if(s != null) {	
		ExaminationSymptom	examSymptom = new ExaminationSymptom();
			examSymptom.setCreatedAt(new Date());
			examSymptom.setExamination(examination);
			examSymptom.setSymptom(s);
			examSymptomRepo.save(examSymptom);
			
		}
	}
	
	@Override
	public void removeExaminationAllPathologies(Long id) {
		repo.removeExaminationAllPathologies(id);
	}
	
	User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public Long findPatientExaminationsNumberByAdmission(Long admissionID) {
		return repo.findPatientExaminationsNumber(admissionID);
	}

	@Override
	public Page<Examination> findPatientFirstExaminationsOfAdmisions(Long patientID, Pageable pageable) {
		System.out.println(patientID);
		return repo.findPatientFirstExaminationsOfAdmisions(patientID, pageable);
	}

	@Override
	public List<Examination> findPatientExaminationsOfLastAdmision(Long patientID) {
		return repo.findPatientExaminationsOfLastAdmision(patientID);
	}

	@Override
	public Long dayNumberBetweenAdmissionFirstExaminationAndCurrentDate(Long admissionID) throws Exception {
		Examination admissionFirstExamination = repo.findAdmissionFirstExamination(admissionID);
		if (admissionFirstExamination == null) {
			return null;
		}
		  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		  Date examDate = new Date(admissionFirstExamination.getStartDate().getTime());
		    Date firstDate = sdf.parse(sdf.format(examDate));
		    Date secondDate = sdf.parse(sdf.format(new Date()));
		    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}

	@Override
	public Examination findAdmissionLastExamination(Long id) {
		return repo.findLastExamination(id);
	}

	@Override
	public Examination insertDiagnostic(Long id, String diagnostic) throws NotFoundException {
		Examination examination = repo.findExaminationById(id).orElse(null);
		if(examination == null) {
			throw new NotFoundException("Consultation non trouvée");
		}
		examination.setConclusion(diagnostic);
		repo.save(examination);
		return examination;
	}


}
