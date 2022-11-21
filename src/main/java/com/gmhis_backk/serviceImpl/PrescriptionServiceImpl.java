package com.gmhis_backk.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Drug;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.PatientPrescription;
import com.gmhis_backk.domain.Prescription;
import com.gmhis_backk.domain.PrescriptionItem;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PrescriptionDto;
import com.gmhis_backk.dto.PrescriptionItemDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PrescriptionItemRepository;
import com.gmhis_backk.repository.PrescriptionRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugService;
import com.gmhis_backk.service.ExaminationService;
import com.gmhis_backk.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	ExaminationService examinationService;
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PrescriptionItemRepository prescriptionItemRepository;
	
	@Autowired 
	DrugService drugService;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override @Transactional
	public Prescription save(PrescriptionDto prescriptionDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		System.out.print(prescriptionDto.getObservation());
		Prescription prescription = new Prescription();
		BeanUtils.copyProperties(prescriptionDto, prescription, "id");
		prescription.setPrescriptionNumber(getPrescriptionNumber());
		 Examination examinnation = examinationService.findExaminationById(prescriptionDto.getExaminationId()).orElse(null);
			if (examinnation == null) {
				throw new ResourceNotFoundByIdException("aucune consultation trouvée pour l'identifiant " );
			}else {
				prescription.setExamination(examinnation);
			}
			prescription.setPrescriptionDate(new Date());
			prescription.setCreatedBy(this.getCurrentUserId().getId());

			Prescription newPrescription = prescriptionRepository.save(prescription);
			
			if (prescriptionDto.getPrescriptionItemsDto().size() != 0) {
				for(PrescriptionItemDto prescriptionItemDto : prescriptionDto.getPrescriptionItemsDto()) {
					Drug drug = new Drug();
					 drug = drugService.findDrugById(prescriptionItemDto.getDrug()).orElse(null);
					 if (drug == null) {
							throw new ResourceNotFoundByIdException("aucun medicament trouvé pour l'identifiant ");
						}
					PrescriptionItem prescriptionItem = new PrescriptionItem();
					prescriptionItem.setCollected(prescriptionItemDto.getCollected());
					prescriptionItem.setDosage(prescriptionItemDto.getDosage());
					prescriptionItem.setPrescription(newPrescription);
					prescriptionItem.setDrug(drug);
					prescriptionItem.setDuration(prescriptionItemDto.getDuration());
					prescriptionItem.setQuantity(prescriptionItemDto.getQuantity());
					prescriptionItemRepository.save(prescriptionItem);
				}
			}
		return prescription;
	}

	@Override
	public Prescription update(PrescriptionDto prescriptionDto, UUID id)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		return null;
	}

	@Override
	public List<Prescription> findAll() {
		return null;
	}

	@Override
	public Page<Prescription> findAll(Pageable pageable) {
		return prescriptionRepository.findAll(pageable);
	}
	
public String getPrescriptionNumber() {
		
		Prescription PrescriptionNumber = prescriptionRepository.findLastPrescription();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lPrescriptionYearandMonth = "";
		String PrescriptionNumberNb = "";
		int  number= 0;
		
		if(PrescriptionNumber ==  null) {
			lPrescriptionYearandMonth = year + month;
			PrescriptionNumberNb = "0000";
		}else {
			 String an = PrescriptionNumber.getPrescriptionNumber().substring(2);
			 lPrescriptionYearandMonth = an.substring(0, 4);
			 PrescriptionNumberNb = an.substring(4);	
		}
		
		if(lPrescriptionYearandMonth.equals( year + month)) {
			number = Integer.parseInt(PrescriptionNumberNb) + 1 ;
		} else {
			number = number +1;
		}
		
		return "OR" + year + month +String.format("%04d", number);
			
	}

}
