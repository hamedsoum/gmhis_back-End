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
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.dto.InsuranceDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.InsurranceRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.InsuranceService;

@Service
public class InsuranceServiceImpl implements InsuranceService {

	@Autowired
	InsurranceRepository insuranceRepository;
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public Page<Insurance> findAllInsurance(Pageable pageable) {
		return insuranceRepository.findAll(pageable);
	}

	@Override
	public Page<Insurance> findAllInsuranceByActiveAndName(String name, Boolean active, Pageable pageable) {
		return insuranceRepository.findAllInsuranceByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<Insurance> findAllInsuranceByName(String name, Pageable pageable) {
		return insuranceRepository.findAllInsuranceByName(name, pageable);
	}

	@Override
	public List<Insurance> findAllInsurances() {
		return insuranceRepository.findAllinsuranceSimpleList();
	}

	@Override
	public void deleteInsurance(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Insurance> getInsuranceDetails(Long id) {
		return insuranceRepository.findById(id);
	}
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional 
	public Insurance addInsurance(InsuranceDto insuranceDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Insurance insuranceByName = insuranceRepository.findByName(insuranceDto.getName());
		if(insuranceByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de l'assurance existe déjà ");  
		} 
		Insurance insurrance = new Insurance();		
		BeanUtils.copyProperties(insuranceDto,insurrance,"id");
		insurrance.setCreatedAt(new Date());
		insurrance.setCreatedBy(getCurrentUserId().getId());
		return insuranceRepository.save(insurrance);
	}

	@Override @Transactional 
	public Insurance updateInsurance(Long id, InsuranceDto insuranceDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
Insurance updateInsurance = insuranceRepository.findById(id).orElse(null);
		
		if (updateInsurance == null) {
			 throw new ResourceNotFoundByIdException("Aucune assurance trouvé pour l'identifiant");
		} else {
			Insurance insuranceByName = insuranceRepository.findByName(insuranceDto.getName());
			if(insuranceByName != null) {
				if(insuranceByName.getId() != updateInsurance.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de l'assurance existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(insuranceDto, updateInsurance,"id");
		updateInsurance.setUpdatedAt(new Date());
		updateInsurance.setUpdatedBy(getCurrentUserId().getId());
		return insuranceRepository.save(updateInsurance);
	}

}
