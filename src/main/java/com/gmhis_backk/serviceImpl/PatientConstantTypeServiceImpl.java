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
import com.gmhis_backk.domain.PatientConstantType;
import com.gmhis_backk.domain.UnitOfMeasure;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PatientConstantTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PatientConstantTypeRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientConstantDomainService;
import com.gmhis_backk.service.PatientConstantTypeService;
import com.gmhis_backk.service.UnitOfMeasureService;

@Service
public class PatientConstantTypeServiceImpl implements PatientConstantTypeService {
	
	@Autowired
	private PatientConstantTypeRepository patientConstantRepository;

	@Autowired
	private PatientConstantDomainService patientConstantDomainService;
	
	
	
	@Autowired private UserRepository userRepository;


	@Override @Transactional
	public PatientConstantType save(PatientConstantTypeDTO pcDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		
		PatientConstantType patientConstantName =  patientConstantRepository.findByName(pcDto.getName());
		
		if (patientConstantName != null) {
			throw new ResourceNameAlreadyExistException("Le nom du type constante de existe déjà "); 
		}
		
		PatientConstantDomain patientConstantDomain = patientConstantDomainService.getConstatDomainDetails(pcDto.getConstantDomain()).orElse(null);
		if (patientConstantDomain == null) {
			throw new ResourceNotFoundByIdException("aucun Domaine d'acte trouvé pour l'identifiant " );
		}
		
		
		
		
		PatientConstantType patientConstantType = new PatientConstantType();
		BeanUtils.copyProperties(pcDto,patientConstantType,"id");
		patientConstantType.setPatientConstantDomain(patientConstantDomain);
		patientConstantType.setCreatedAt(new Date());
		patientConstantType.setCreatedBy(getCurrentUserId().getId());
		return patientConstantRepository.save(patientConstantType);
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public PatientConstantType update(Long id, PatientConstantTypeDTO pcDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		PatientConstantType updatePatientConstantType = patientConstantRepository.findById(id).orElse(null);
		if (updatePatientConstantType == null) {
			 throw new ResourceNotFoundByIdException("Aucun type de constante trouvé pour l'identifiant");
		}else {
			PatientConstantType patientConstantTypeByName = patientConstantRepository.findByName(pcDto.getName());
			if (patientConstantTypeByName != null) {
				if(patientConstantTypeByName.getId() != updatePatientConstantType.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom du type de constante existe déjà");
				}
			}
		}
		
		PatientConstantDomain patientConstantDomain = patientConstantDomainService.getConstatDomainDetails(pcDto.getConstantDomain()).orElse(null);
		if (patientConstantDomain == null) {
			throw new ResourceNotFoundByIdException("aucun Domaine d'acte trouvé pour l'identifiant " );
		}
		
		
			BeanUtils.copyProperties(pcDto,updatePatientConstantType,"id");
			updatePatientConstantType.setPatientConstantDomain(patientConstantDomain);
			updatePatientConstantType.setUpdatedAt(new Date());
			updatePatientConstantType.setUpdatedBy(getCurrentUserId().getId());
			return patientConstantRepository.save(updatePatientConstantType);
	}

	@Override
	public PatientConstantType findByName(String constantType) {
		return patientConstantRepository.findByName(constantType);
	}

	@Override
	public Optional<PatientConstantType> findById(Long id) {
		return patientConstantRepository.findById(id);
	}

	@Override
	public List<PatientConstantType> findAll() {
		return patientConstantRepository.findAll();
	}

	@Override
	public Page<PatientConstantType> findAll(Pageable pageable) {
		return patientConstantRepository.findAll(pageable);
	}

	@Override
	public Page<PatientConstantType> findPatientConstantTypesContaining(String name, Pageable pageable) {
		return patientConstantRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<PatientConstantType> findActivePatientConstantTypes() {
		return patientConstantRepository.findActivePatientConstantTypes();
	}

	@Override
	public List<PatientConstantType> findActivePatientConstantTypesByDomain(Long domId) {
		return patientConstantRepository.findActivePatientConstantTypesByDomain(domId);
	}

	@Override
	public Page<PatientConstantType> findByActive(String name, Boolean active, Pageable pageable) {
		return patientConstantRepository.findByActive(name, active, pageable);
	}

	@Override
	public Page<PatientConstantType> findByDomain(Long domain, String name, Pageable pageable) {
		return patientConstantRepository.findByDomain(domain, name, pageable);
	}

	@Override
	public Page<PatientConstantType> findByDomainAndActive(Long domain, String name, Boolean active, Pageable pageable) {
		return patientConstantRepository.findByDomainAndActive(domain, name, active, pageable);
	}

}
