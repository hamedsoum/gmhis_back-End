package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.repository.PracticianRepository;
import com.gmhis_backk.service.PracticianService;


/**
 * 
 * @author Dabre
 *
 */
@Service
@Transactional
public class PracticianServiceImpl implements PracticianService{

	@Autowired
	private PracticianRepository repo;

	
	public Pratician findPracticianByUser (Long user) {
		return repo.findByUser(user);
	}

	@Override
	public Pratician savePractician(Pratician p) {
		return repo.save(p);
	}


	@Override
	public Optional<Pratician> findPracticianById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Pratician> findPracticians() {
		return repo.findAll();
	}

	@Override
	public Page<Pratician> findAllP(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	public List<Pratician> findActivePracticians() {
		return repo.findActivePracticians();
	}


	@Override
	public Page<Pratician> findByActive(String firstName, String lastName, String phoneContact, String practicianNumber, String active, Pageable pageable){
		return repo.findByActive(firstName,  lastName,  phoneContact, practicianNumber, active, pageable);	
	}
	
	
	@Override
	public Page<Pratician> findPracticians(String firstName, String lastName, String phoneContact, String practicianNumber, Pageable pageable) {
		return repo.findPracticians(firstName, lastName, phoneContact, practicianNumber, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByService(String firstName, String lastName, String phoneContact, String practicianNumber, Long service, Pageable pageable) {
		return repo.findByService(firstName, lastName, phoneContact, practicianNumber, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findBySpeciality(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Pageable pageable) {
		return repo.findByService(firstName, lastName, phoneContact, practicianNumber, speciality, pageable);
	}
	
	
	@Override
	public Page<Pratician> findBySpecialityAndService(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Long service, Pageable pageable) {
		return repo.findBySpecialityAndService(firstName, lastName, phoneContact, practicianNumber, speciality, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByActiveAndSpeciality (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long speciality, Pageable pageable){
		return repo.findByActiveAndSpeciality(firstName, lastName, phoneContact, practicianNumber, active, speciality, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByActiveAndService (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long service, Pageable pageable){
		return repo.findByActiveAndSpeciality(firstName, lastName, phoneContact, practicianNumber, active, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findPracticiansByAllFilters (String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Long service, String active, Pageable pageable){
		return repo.findPracticiansByAllFilters(firstName, lastName, phoneContact, practicianNumber, speciality, service, active, pageable);
	}
	
	@Override
	public List<Pratician> findActivePracticiansByService(Long service){
		return repo.findActivePracticiansByService(service);
	}
	
	
}
