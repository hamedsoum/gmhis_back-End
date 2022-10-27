package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ConventionHasActCodeRepository;
import com.gmhis_backk.repository.ConventionHasActRepository;
import com.gmhis_backk.repository.ConventionRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ConventionService;

@Service
public class ConventionServiceImpl implements ConventionService{
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private ConventionRepository repo;
	
	@Autowired
	private ConventionHasActCodeRepository conventionActCodeRepo;
	
	@Autowired
	private ConventionHasActRepository conventionActRepo;
	
	@Override @Transactional
	public Convention saveConvention(Convention c)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		 		return repo.save(c);
	}


	
	@Override
	public Convention findConventionByName(String Convention) {
		return repo.findByName(Convention);
	}

	@Override
	public Optional<Convention> findConventionById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Convention> findConventions() {
		return repo.findAll();
	}

	@Override
	public Page<Convention> findConventions(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Page<Convention> findConventionsContaining(String name, Pageable pageable) {
		return repo.findAllConventionByName(name, pageable);
	}

	@Override
	public List<Convention> findActiveConventions() {
		return repo.findAllActive();
	}

	@Override
	public Page<Convention> findByActive(String namme, Boolean active, Pageable pageable) {
		return repo.findAllConventionByActiveAndName(namme, active, pageable);
	}

	@Override
	public void addActToConvention(ConventionHasAct conventionAct) {
		if (ObjectUtils.isNotEmpty(conventionAct)) {
			conventionActRepo.save(conventionAct);
		} else {
			LOGGER.error("addActToConvention: convention has act");
		}		
	}

	@Override
	public void removeActToConvention(Convention convention, ConventionHasAct conventionAct) {
		 conventionActRepo.removeAct(conventionAct.getId(), convention.getId());
		
	}

	@Override
	public void removeAllActs(Convention convention) {
		 conventionActRepo.removeAllActs(convention.getId());
		
	}

	@Override
	public void addActCodeToConvention(ConventionHasActCode conventionActCode) {
		if (ObjectUtils.isNotEmpty(conventionActCode)) {
			conventionActCodeRepo.save(conventionActCode);
		} else {
			LOGGER.error("addActCodeToConvention: convention has act code");
		}		
	}

	@Override
	public void removeActCodeToConvention(Convention convention, ConventionHasActCode conventionActCode) {
		conventionActRepo.removeAct(conventionActCode.getId(), convention.getId());
		
	}

	@Override
	public void removeAllActCodes(Convention convention) {
		conventionActCodeRepo.removeAllActCodes(convention.getId());
		
	}
	
	

}
