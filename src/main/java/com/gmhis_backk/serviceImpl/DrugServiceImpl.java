package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Drug;
import com.gmhis_backk.domain.DrugDci;
import com.gmhis_backk.domain.DrugPharmacologicalForm;
import com.gmhis_backk.domain.DrugTherapeuticClass;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DrugDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.DrugRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugDciService;
import com.gmhis_backk.service.DrugPharmacologicalFormService;
import com.gmhis_backk.service.DrugService;
import com.gmhis_backk.service.DrugTherapeuticClassService;

@Service
public class DrugServiceImpl implements DrugService {

	@Autowired
	DrugRepository drugRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DrugDciService drugDciService;
	
	@Autowired
	private DrugPharmacologicalFormService drugPharmacologicalFormService;
	
	@Autowired
	private DrugTherapeuticClassService drugTherapeuticClassService;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public Drug saveDrug(DrugDto dDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Drug drugByName = drugRepository.findDrugByName(dDto.getName());
		if (drugByName != null) {
			throw new ResourceNameAlreadyExistException("le medicament existe déjà");
		}
		
		 DrugDci drugDci = drugDciService.getDrugDciDetails(dDto.getDrugDci()).orElse(null);
			if (drugDci == null) {
				throw new ResourceNotFoundByIdException("aucun DCI trouvé pour l'identifiant " );
			}
			System.out.print(dDto.getDrugPharmacologicalForm());
			DrugPharmacologicalForm drugPharmcoloForm = drugPharmacologicalFormService.findFormById(dDto.getDrugPharmacologicalForm()).orElse(null);
			if (drugPharmcoloForm == null) {
				throw new ResourceNotFoundByIdException("aucune forme pharmacologique trouvée pour l'identifiant " );
			}
		
			 DrugTherapeuticClass drugTherapeuticClass = drugTherapeuticClassService.getDrugTherapeuticClassDetails(dDto.getDrugTherapeuticClass()).orElse(null);
				if (drugTherapeuticClass == null) {
					throw new ResourceNotFoundByIdException("aucune classe therapeuttique trouvée pour l'identifiant " );
				}
			
		
		Drug drug = new Drug();
		BeanUtils.copyProperties(dDto,drug,"id");
		drug.setDrugDci(drugDci);
		drug.setDrugPharmacologicalForm(drugPharmcoloForm);
		drug.setDrugTherapeuticClass(drugTherapeuticClass);
		drug.setCreatedAt(new Date());
		drug.setCreatedBy(this.getCurrentUserId().getId());
		return drugRepository.save(drug);
	}

	@Override
	public Drug updateDrug(DrugDto dDto, UUID Id) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
	Drug updateDrug = drugRepository.findById(Id).orElse(null);
		
		if (updateDrug == null) {
			 throw new ResourceNotFoundByIdException("Aucun medicament trouvé pour l'identifiant");
		} else {
			Drug drugByName = drugRepository.findDrugByName(dDto.getName());
			if (drugByName != null) {
				if (drugByName.getId() != updateDrug.getId()) {
					throw new ResourceNameAlreadyExistException("le nom du type de centre sante existe deja");
				}
			}
		}
		BeanUtils.copyProperties(dDto, updateDrug,"id");
		return drugRepository.save(updateDrug);
	}

	@Override
	public Drug findDrugByName(String drug) {
		return drugRepository.findDrugByName(drug);
	}

	@Override
	public Optional<Drug> findDrugById(UUID id) {
		return drugRepository.findById(id);
	}


	@Override
	public Page<Drug> findDrugs(Pageable pageable) {
		return drugRepository.findAll(pageable);
	}

	@Override
	public Page<Drug> findDrugsContaining(String name, Pageable pageable) {
		return drugRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Drug> findActiveDrugsType() {
		return drugRepository.findActiveDrugs();
	}

	@Override
	public Page<Drug> findByActive(String namme, Boolean active, Pageable pageable) {
		return drugRepository.findByActive(namme, active, pageable);
	}

	@Override
	public Page<Drug> finddrugByActiveAndNameAndDrugDci(String namme, Boolean active, UUID drugDci,
			Pageable pageable) {
		return drugRepository.findDrugByNameAndActiceAndDci(namme, active, drugDci, pageable);
	}

	@Override
	public Page<Drug> finddrugByDrugDci(UUID drugDci, Pageable pageable) {
		return drugRepository.findDrugBydDci(drugDci, pageable);
	}

	
 
}
