package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Insured;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface InsuredService {
	public Insured saveInsured(Insured i);

	public Insured findInsuredByCardNumber(String cardNumber);

	public List<Insured> findInsuredByPatient(Long patient);

	public Optional<Insured> findInsuredById(Long id);

	public List<Insured> findInsureds();
	
	public Page<Insured> findInsureds(Pageable pageable);
	
	public List<Insured> findActiveInsureds();
	
	public Page<Insured> findByActive(String active, Pageable pageable);
	
	public void deleteInsuredsByPatient(Long patient);
}
