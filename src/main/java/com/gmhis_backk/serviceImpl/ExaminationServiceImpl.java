package com.gmhis_backk.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.ExaminationHasPathology;
import com.gmhis_backk.domain.ExaminationSymptom;
import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.domain.Symptom;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.repository.ExaminationHasPathologyRepository;
import com.gmhis_backk.repository.ExaminationRepository;
import com.gmhis_backk.repository.ExaminationSymptomRepository;
import com.gmhis_backk.repository.PathologyRepository;
import com.gmhis_backk.repository.SymptomRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ExaminationService;

import javassist.NotFoundException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class ExaminationServiceImpl implements ExaminationService{

	@Autowired
	private ExaminationRepository repo;
	
	@Autowired
	PathologyRepository pathologyRepo;
	
	@Autowired
	SymptomRepository symptomRepo;
	
	@Autowired
	ExaminationSymptomRepository examSymptomRepo;
	
	@Autowired
	ExaminationHasPathologyRepository examPathologyRepo;
	
	@Autowired 
	UserRepository userRepository;
	
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
			examPatholy.setCreatedBy(this.getCurrentUserId().getId());
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
	
	User getCurrentUserId() {
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
		System.out.println(id);
		Examination examination = repo.findExaminationById(id).orElse(null);
		if(examination == null) {
			throw new NotFoundException("Consultation non trouvée");
		}
		examination.setConclusion(diagnostic);
		repo.save(examination);
		return examination;
	}
}
