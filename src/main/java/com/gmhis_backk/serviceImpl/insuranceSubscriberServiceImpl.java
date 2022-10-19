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
import com.gmhis_backk.domain.InsuranceSuscriber;
import com.gmhis_backk.dto.InsuranceSubscriberDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.InsuranceSubscriberRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.InsuranceSuscriberService;

@Service
public class insuranceSubscriberServiceImpl implements InsuranceSuscriberService {
	
	@Autowired
	InsuranceSubscriberRepository insuranceSubscriberRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Page<InsuranceSuscriber> findAllInsuranceSuscriber(Pageable pageable) {
		return insuranceSubscriberRepository.findAllInsuranceSuscriber(pageable);
	}

	@Override
	public Page<InsuranceSuscriber> findAllInsuranceSuscriberByActiveAndName(String name, Boolean active,
			Pageable pageable) {
		return insuranceSubscriberRepository.findAllInsuranceSuscriberByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<InsuranceSuscriber> findAllInsuranceSuscriberByName(String name, Pageable pageable) {
		return insuranceSubscriberRepository.findAllInsuranceSuscriberByName(name, pageable);
	}

	@Override
	public List<InsuranceSuscriber> findAllInsuranceSuscribers() {
		return insuranceSubscriberRepository.findAllInsuranceSuscriberSimpleList();
	}

	@Override
	public void deleteInsuranceSuscriber(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override 
	public Optional<InsuranceSuscriber> getInsuranceSuscriberDetails(Long id) {
		return insuranceSubscriberRepository.findById(id);
	}

	@Override  @Transactional 
	public InsuranceSuscriber addInsuranceSuscriber(InsuranceSubscriberDto insuranceSubscriberDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		InsuranceSuscriber insuranceSuscriberByName = insuranceSubscriberRepository.findByName(insuranceSubscriberDto.getName());
		if(insuranceSuscriberByName!=null) {
			throw new ResourceNameAlreadyExistException("l'etablissement garant existe déjà ");  
		} 
		InsuranceSuscriber insuranceSuscriber = new InsuranceSuscriber();		
		BeanUtils.copyProperties(insuranceSubscriberDto,insuranceSuscriber,"id");
		insuranceSuscriber.setCreatedAt(new Date());
		insuranceSuscriber.setCreatedBy(getCurrentUserId().getId());
		return insuranceSubscriberRepository.save(insuranceSuscriber);
	}

	@Override  @Transactional
	public InsuranceSuscriber updateInsuranceSuscriber(Long id, InsuranceSubscriberDto insuranceSubscriberDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		InsuranceSuscriber updateInsuranceSubscriber = insuranceSubscriberRepository.findById(id).orElse(null);
		
		if (updateInsuranceSubscriber == null) {
			 throw new ResourceNotFoundByIdException("Aucun etablissement garant trouvé pour l'identifiant");
		} else {
			InsuranceSuscriber insuranceSuscriberByName = insuranceSubscriberRepository.findByName(insuranceSubscriberDto.getName());
			if(insuranceSuscriberByName != null) {
				if(insuranceSuscriberByName.getId() != updateInsuranceSubscriber.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de l'etablissement existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(insuranceSubscriberDto, updateInsuranceSubscriber,"id");
		updateInsuranceSubscriber.setUpdatedAt(new Date());
		updateInsuranceSubscriber.setUpdatedBy(getCurrentUserId().getId());
		return insuranceSubscriberRepository.save(updateInsuranceSubscriber);
	}

	@Override
	public List<InsuranceSuscriber> findAllActiveInsuranceSuscribers() {
		// TODO Auto-generated method stub
		return insuranceSubscriberRepository.findAllActive();
	}

	
	

}
