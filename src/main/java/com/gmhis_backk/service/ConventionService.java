package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface ConventionService {
	public Convention saveConvention(Convention c) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Convention findConventionByName(String Convention);
	
	public Optional<Convention> findConventionById(Long id);

	public List<Convention> findConventions();
	
	public Page<Convention> findConventions(Pageable pageable);
	
	public Page<Convention> findConventionsContaining(String name,Pageable pageable);
	
	public List<Convention> findActiveConventions();
	
	public Page<Convention> findByActive(String namme, Boolean active, Pageable pageable);
	
	public void addActToConvention(ConventionHasAct conventionAct);
	
	public void removeActToConvention(Convention convention, ConventionHasAct conventionAct);
	
	public void removeAllActs(Convention convention);
	
	public void addActCodeToConvention(ConventionHasActCode conventionActCode);
	
	public void removeActCodeToConvention(Convention convention, ConventionHasActCode conventionActCode);
	
	public void removeAllActCodes(Convention convention);
}
