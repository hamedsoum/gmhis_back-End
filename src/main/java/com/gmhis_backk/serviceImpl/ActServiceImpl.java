package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.AdmissionHasAct;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.MedicalAnalysisSpecilaity;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ActRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;
import com.gmhis_backk.service.ActCodeService;
import com.gmhis_backk.service.ActGroupService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.MedicalAnalysisSpecilaityService;

@Service
public class ActServiceImpl implements ActService{

	@Autowired
	private ActRepository actRepository;
	
	@Autowired
	private ActGroupService actGroupService;
	
	@Autowired
	private ActCategoryService actCategoryService;
	
	@Autowired
	private ActCodeService actCodeService;
	
	@Autowired
	private MedicalAnalysisSpecilaityService medicalAnalysisSpecilaityService;
	@Autowired private UserRepository userRepository;
	@Override
	public List<Act> findActs() {
		return actRepository.findAll();
	}

	@Override
	public Page<Act> findActs(Pageable pageable) {
		return actRepository.findAll(pageable);
	}

	@Override
	public Page<Act> findActsContaining(String name, Pageable pageable) {
		return  actRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Act> findActiveActs() {
		return actRepository.findActiveActs();
	}

	@Override
	public List<Act> findActListByName(String name) {
		return actRepository.findActListByName(name);
	}

	@Override
	public Page<Act> findByActive(String name, Boolean active,Long category, Pageable pageable) {
		return actRepository.findByActive(name, active,category, pageable);
	}

	@Override
	public Page<Act> findActiveActsByCategory(String name, Long category, Pageable pageable) {
		return actRepository.findByCategory(name, category,pageable);
	}

	@Override
	public List<Act> findActiveActsByGroup(String name, Long group) {
		return actRepository.findByGroup(name, group);
	}

	@Override
	public List<Act> findActiveActsByCriteria(String name, Long group, Long category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConventionHasAct findActByConventionAndAct(Convention convention, Act act) {
		return actRepository.findActByConventionAndAct(convention.getId(), act.getId());
	}

	@Override
	public List<AdmissionHasAct> findActsByBill(Long billID) {
		return actRepository.findActsByBill(billID);
	}

	@Override
	public Optional<Act> findActById(Long id) {
		return actRepository.findById(id);
	}
	
	protected  User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public Act addAct(ActDTO actDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		 MedicalAnalysisSpecilaity medicalAnalysisSpecilaity  = null;
		Act actByName = actRepository.findByName(actDto.getName());
		if (actByName != null) {
			throw new ResourceNameAlreadyExistException("Le nom de l'acte existe déjà "); 
		}
		
	 ActCategory actCategory = actCategoryService.getActCategoryDetails(actDto.getActCategory()).orElse(null);
		if (ObjectUtils.isNotEmpty(actDto.getActCategory()) && actCategory == null) {
			throw new ResourceNotFoundByIdException("aucune categorie d'acte trouvé pour l'identifiant " );
		}
		
	 ActGroup actGroup = actGroupService.getActGroupDetails(actDto.getActGroup()).orElse(null);
			if (actGroup == null) {
				throw new ResourceNotFoundByIdException("aucune famille d'acte trouvé pour l'identifiant ");
	   }
		
	 ActCode actCode = actCodeService.getActCodeDetails(actDto.getActCode()).orElse(null);
				if (actCode == null) {
					throw new ResourceNotFoundByIdException("aucun code d'acte d'acte trouvé pour l'identifiant ");
				}
	
				if (ObjectUtils.isNotEmpty(actDto.getMedicalAnalysisSpeciality())) {
					  medicalAnalysisSpecilaity = medicalAnalysisSpecilaityService.getMedicalAnalysisSpecilaityDetails(actDto.getMedicalAnalysisSpeciality()).orElse(null);
						if (medicalAnalysisSpecilaity == null) {
								throw new ResourceNotFoundByIdException("aucune specialite trouvée pour l'identifiant ");
							}	
				}

		Act act = new Act();
		BeanUtils.copyProperties(actDto,act,"id");
		act.setActCategory(actCategory);
		act.setActGroup(actGroup);
		act.setActCode(actCode);
		act.setMedicalAnalysisSpeciality(medicalAnalysisSpecilaity);
		act.setCreatedAt(new Date());
		act.setCreatedBy(getCurrentUserId().getId());
		return actRepository.save(act);
	}

	
	@Override @Transactional
	public Act updateAct(Long id, ActDTO actDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		 MedicalAnalysisSpecilaity medicalAnalysisSpecilaity  = null;
		Act updateActe = actRepository.findById(id).orElse(null);
		if (updateActe == null) {
			 throw new ResourceNotFoundByIdException("Aucun acte trouvé pour l'identifiant");
		}else {
			Act actByName = actRepository.findByName(actDto.getName());
			if (actByName != null) {
				if(actByName.getId() != updateActe.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de l'acte existe déjà");
				}
			}
		}
		
		 ActCategory actCategory = actCategoryService.getActCategoryDetails(actDto.getActCategory()).orElse(null);
			if (actCategory == null) {
				throw new ResourceNotFoundByIdException("aucune categorie d'acte trouvé pour l'identifiant " );
			}
			
			 ActGroup actGroup = actGroupService.getActGroupDetails(actDto.getActGroup()).orElse(null);
				if (actGroup == null) {
					throw new ResourceNotFoundByIdException("aucune famille d'acte trouvé pour l'identifiant" );
				}
			
		    ActCode actCode = actCodeService.getActCodeDetails(actDto.getActCode()).orElse(null);
			if (actCode == null) {
				throw new ResourceNotFoundByIdException("aucun code d'acte d'acte trouvé pour l'identifiant ");
			}
			
			if (ObjectUtils.isNotEmpty(actDto.getMedicalAnalysisSpeciality())) {
				  medicalAnalysisSpecilaity = medicalAnalysisSpecilaityService.getMedicalAnalysisSpecilaityDetails(actDto.getMedicalAnalysisSpeciality()).orElse(null);
					if (medicalAnalysisSpecilaity == null) {
							throw new ResourceNotFoundByIdException("aucune specialite trouvée pour l'identifiant ");
						}	
					updateActe.setMedicalAnalysisSpeciality(medicalAnalysisSpecilaity);
			}
			
			BeanUtils.copyProperties(actDto,updateActe,"id");
			updateActe.setActCategory(actCategory);
			updateActe.setActGroup(actGroup);
			updateActe.setActCode(actCode);
			updateActe.setUpdatedAt(new Date());
			updateActe.setUpdatedBy(getCurrentUserId().getId());
			return actRepository.save(updateActe);
		
	}

	@Override
	public List<Act> findActiveNamesAndIdsActsByCategory(Long category) {
		return actRepository.findNamesAndIdsByCategory(category);
	}

	@Override
	public List<Act> findNamesAndIdsByMedicalAnalysisSpeciality() {
		return actRepository.findNamesAndIdsByMedicalAnalysisSpeciality();
	}


}
