package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.Optional;

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
	public Page<Examination> findPatientExaminations(Long patient, Pageable pageable){
		return repo.findPatientExaminations(patient, pageable);
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
	public Long findPatientExaminationsNumber(Long patientId) {
		return repo.findPatientExaminationsNumber(patientId);
	}
}
