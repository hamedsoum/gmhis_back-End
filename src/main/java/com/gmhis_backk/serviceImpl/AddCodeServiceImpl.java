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
import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActCodeDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ActCodeRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCodeService;

@Service
public class AddCodeServiceImpl implements ActCodeService {

	@Autowired
	ActCodeRepository actCodeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public Page<ActCode> findAllActCode(Pageable pageable) {
		return actCodeRepository.findAll(pageable);
	}

	@Override
	public Page<ActCode> findAllActCodeByActiveAndName(String name, Boolean active, Pageable pageable) {
		return actCodeRepository.findAllActCodeByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<ActCode> findAllActCodeByName(String name, Pageable pageable) {
		return actCodeRepository.findAllActCodeByName(name, pageable);
	}

	@Override
	public List<ActCode> findAllActCodes() {
		return actCodeRepository.findAllActCodeSimpleList();
	}

	@Override
	public void deleteActCode(Integer id) {
		
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public Optional<ActCode> getActCodeDetails(Long id) {
		return actCodeRepository.findById(id);
	}

	@Override @Transactional
	public ActCode addActCode(ActCodeDto actCodeDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		ActCode actCodeByName = actCodeRepository.findByName(actCodeDto.getName());
		if(actCodeByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom du code existe déjà ");  
		} 
		ActCode actCode = new ActCode();		
		BeanUtils.copyProperties(actCodeDto,actCode,"id");
		actCode.setCreatedAt(new Date());
		actCode.setCreatedBy(getCurrentUserId().getId());
		return actCodeRepository.save(actCode);	
		
	}

	@Override @Transactional
	public ActCode updateActCode(Long id, ActCodeDto actCodeDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		ActCode updateActCode = actCodeRepository.findById(id).orElse(null);
		
		if (updateActCode == null) {
			 throw new ResourceNotFoundByIdException("Aucun code trouvé pour l'identifiant");
		} else {
			ActCode actGroupByName = actCodeRepository.findByName(actCodeDto.getName());
			if(actGroupByName != null) {
				if(actGroupByName.getId() != updateActCode.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom du code existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(actCodeDto, updateActCode,"id");
		updateActCode.setUpdatedAt(new Date());
		updateActCode.setUpdatedBy(getCurrentUserId().getId());
		return actCodeRepository.save(updateActCode);
	}

	@Override
	public List<ActCode> findAllActive() {
		return actCodeRepository.findAllActive();
	}

	@Override
	public ConventionHasActCode findActCodeByConventionAndAct(Convention convention, ActCode actCode) {
		return actCodeRepository.findActCodeByConventionAndAct(convention.getId(), actCode.getId());
	}

}
