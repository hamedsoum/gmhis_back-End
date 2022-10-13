package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Insured;
import com.gmhis_backk.repository.InsuredRepository;
import com.gmhis_backk.service.InsuredService;

@Service
public class InsuredServiceImp implements InsuredService {

	@Autowired
	InsuredRepository repo ;
	@Override
	public Insured saveInsured(Insured i) {
		return repo.save(i);
	}

	@Override
	public Insured findInsuredByCardNumber(String cardNumber) {
		return repo.findInsuredByCardNumber(cardNumber);
	}
	
	@Override
	public List<Insured> findInsuredByPatient(Long patient){
		return repo.findInsuredByPatient(patient);
	}
	
	@Override
	public Optional<Insured> findInsuredById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Insured> findInsureds(){
		return repo.findAll();
	}
	
	@Override
	public Page<Insured> findInsureds(Pageable pageable){
		return repo.findAll(pageable);
	}
	
	@Override
	public List<Insured> findActiveInsureds(){
		return repo.findActiveInsureds();
	}
	
	@Override
	public Page<Insured> findByActive(String active, Pageable pageable){
		return repo.findByActive(active, pageable);
	}
	
	@Override
	public void deleteInsuredsByPatient(Long patient) {
		repo.deleteByPatientId(patient);
	}

	
}
