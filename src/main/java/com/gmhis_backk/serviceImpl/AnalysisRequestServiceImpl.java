package com.gmhis_backk.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.gmhis_backk.domain.*;
import com.gmhis_backk.service.BillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.admission.Admission;
import com.gmhis_backk.dto.AnalysisRequestDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AnalysisRequestItemRepository;
import com.gmhis_backk.repository.AnalysisRequestRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.AnalysisRequestService;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class AnalysisRequestServiceImpl implements AnalysisRequestService {

	private final AnalysisRequestRepository analysisRequestRepository;
	
	private final AnalysisRequestItemRepository analysisRequestItemRepository;

	private final AdmissionService admissionService;

	private final ActService actService;

	private final UserRepository userRepository;

	private final BillService billService;
	public AnalysisRequestServiceImpl(AnalysisRequestRepository analysisRequestRepository, AnalysisRequestItemRepository analysisRequestItemRepository, AdmissionService admissionService, ActService actService, UserRepository userRepository, BillService billService) {
		this.analysisRequestRepository = analysisRequestRepository;
		this.analysisRequestItemRepository = analysisRequestItemRepository;
		this.admissionService = admissionService;
		this.actService = actService;
		this.userRepository = userRepository;
		this.billService = billService;
	}

	protected  User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public AnalysisRequest saveAnalysisRequest(AnalysisRequestDTO analysDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {

		Admission admission =  admissionService.retrieve(analysDto.getAdmission()).orElse(null);
		if (admission == null) throw new ResourceNotFoundByIdException("aucune admission trouvée pour l'identifiant " );

		Bill bill = billService.findBillById(analysDto.getBillId()).orElse(null);
		if(bill == null) throw new ResourceNotFoundByIdException("Facture inexistnte" );

		AnalysisRequest analys = new AnalysisRequest();
		analys.setBill(bill);
		analys.setExamenType(analysDto.getExamenTytpe());
		analys.setAnalysisNumber(getanalysisNumber());
		analys.setAdmission(admission);
		analys.setCreatedAt(new Date());
		analys.setObservation(analysDto.getObservation());
		analys.setPratician(getCurrentUserId());
		analys.setState('N');
		analys.setCreatedAt(new Date());
		analys.setFacility(analys.getPratician().getFacility());
		analysisRequestRepository.save(analys);
		for(Long a : analysDto.getActs()) {
			Act act = actService.findActById(a).orElse(null);
			if (act == null) {
				throw new ResourceNotFoundByIdException("aucun acte trouvée pour l'identifiant "+ a  );
			}
			AnalysisRequestItem analysisRequestItem = new AnalysisRequestItem();
			analysisRequestItem.setAct(act);
			analysisRequestItem.setAnalysisRequest(analys);
			analysisRequestItem.setState(false);
			analysisRequestItem.setCreatedAt(new Date());
			analysisRequestItemRepository.save(analysisRequestItem);
		}
		return null;
	}

	@Override
	public Optional<AnalysisRequest> findAnalysisRequestById(UUID id) {
		return analysisRequestRepository.findById(id);
	}

	@Override
	public Page<AnalysisRequest> findAnalysisRequestsByPatient(Long patient,Long admissionID, Pageable pageable) {
		return analysisRequestRepository.findAnalysisRequestByPatient(patient,admissionID, pageable);
	}

	@Override
	public Page<AnalysisRequest> findAll(Pageable pageable) {
		return analysisRequestRepository.findAllAnalysis(pageable);
	}

	@Override
	public Page<AnalysisRequest> findAllAnalysisRequests(Boolean examenType, Pageable pageable) {
		if (examenType == true) return analysisRequestRepository.findAllAnalysisRequestsByFacility(examenType,UUID.fromString(getCurrentUserId().getFacilityId()), pageable);
		
		return analysisRequestRepository.findAllAnalysis(pageable);
	}
	

	@Override
	public Page<AnalysisRequest> findAnalysisRequestsByAdmissionNumber(String admissionNumber, Pageable pageable) {
		return analysisRequestRepository.findAnalysisRequestsByAdmissionNumber(admissionNumber, pageable);
	}

	@Override
	public Long findAnalyseNumber(Long admissionId) {
		return analysisRequestRepository.findAnalyseRequestNumber(admissionId);
	}

	   public String getanalysisNumber() {
			
			AnalysisRequest analysisNumber = analysisRequestRepository.findLastAnalysisMedical();
			Calendar calendar = Calendar.getInstance();
			String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
			String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
			String lanalysisYearandMonth = "";
			String analysisNumberNb = "";
			int  number= 0;
			
			if(analysisNumber ==  null) {
				lanalysisYearandMonth = year + month;
				analysisNumberNb = "0000";
			}else {
				 String an = analysisNumber.getAnalysisNumber().substring(2);
				 lanalysisYearandMonth = an.substring(0, 4);
				 analysisNumberNb = an.substring(4);	
			}
			
			if(lanalysisYearandMonth.equals( year + month)) {
				number = Integer.parseInt(analysisNumberNb) + 1 ;
			} else {
				number = number +1;
			}
			
			return "MA" + year + month +String.format("%04d", number);
				
		}
	
}
