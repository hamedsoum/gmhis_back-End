package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.PatientConstantDomain;
import com.gmhis_backk.dto.PatientConstantDomainDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PatientConstantDomainRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientConstantDomainService;

/**
 * 
 * @author Pascal
 *
 */
@Service
public class PatientConstantDomainServiceImpl implements PatientConstantDomainService {

	@Autowired
	PatientConstantDomainRepository patientConstantDomainRepository;
	
	@Autowired
	UserRepository userRepository;
	@Override
	public Page<PatientConstantDomain> findAllConstatDomain(Pageable pageable) {
		return patientConstantDomainRepository.findAll(pageable);
	}

	@Override
	public Page<PatientConstantDomain> findAllConstatDomainByActiveAndName(String name, Boolean active,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return patientConstantDomainRepository.findAllConstantDomainByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<PatientConstantDomain> findAllConstatDomainByName(String name, Pageable pageable) {
		return patientConstantDomainRepository.findAllConstantDomainByName(name, pageable);
	}

	@Override
	public List<PatientConstantDomain> findAllConstatDomains() {
		return patientConstantDomainRepository.findAllConstantDomainSimpleList();
	}

	@Override
	public void deleteConstatDomain(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PatientConstantDomain> getConstatDomainDetails(Long id) {
		// TODO Auto-generated method stub
		return patientConstantDomainRepository.findById(id);
	}
	

	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional 
	public PatientConstantDomain addConstatDomain(PatientConstantDomainDto patientConstantDomainDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		PatientConstantDomain constantDomainByName = patientConstantDomainRepository.findByName(patientConstantDomainDto.getName());
		if(constantDomainByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la constante existe déjà ");  
		} 
		PatientConstantDomain constantDomain = new PatientConstantDomain();		
		BeanUtils.copyProperties(patientConstantDomainDto,constantDomain,"id");
		constantDomain.setCreatedAt(new Date());
		constantDomain.setCreatedBy(getCurrentUserId().getId());
		return patientConstantDomainRepository.save(constantDomain);
	}

	@Override @Transactional 
	public PatientConstantDomain updateConstantDomain(Long id, PatientConstantDomainDto patientConstantDomainDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		PatientConstantDomain updateConstDomain = patientConstantDomainRepository.findById(id).orElse(null);
		
		if (updateConstDomain == null) {
			 throw new ResourceNotFoundByIdException("Aucune famille trouvé pour l'identifiant");
		} else {
			PatientConstantDomain constantDomainByName = patientConstantDomainRepository.findByName(patientConstantDomainDto.getName());
			if(constantDomainByName != null) {
				if(constantDomainByName.getId() != updateConstDomain.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de du groupe de constante existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(patientConstantDomainDto, updateConstDomain,"id");
		updateConstDomain.setUpdatedAt(new Date());
		updateConstDomain.setUpdatedBy(getCurrentUserId().getId());
		return patientConstantDomainRepository.save(updateConstDomain);
	}

}
