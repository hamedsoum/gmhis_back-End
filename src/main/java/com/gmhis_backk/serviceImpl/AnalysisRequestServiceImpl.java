package com.gmhis_backk.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.AnalysisRequest;
import com.gmhis_backk.domain.AnalysisRequestItem;
import com.gmhis_backk.domain.User;
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

	@Autowired
	private AnalysisRequestRepository analysisRequestRepository;
	
	@Autowired
	private AnalysisRequestItemRepository analysisRequestItemRepository;
	
	@Autowired
	private AdmissionService admissionService;
	
	@Autowired
	private ActService actService;
	
	@Autowired private UserRepository userRepository;

	protected  User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public AnalysisRequest saveAnalysisRequest(AnalysisRequestDTO analysDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Admission admission =  admissionService.findAdmissionById(analysDto.getAdmission()).orElse(null);
		
		if (admission == null) {
			throw new ResourceNotFoundByIdException("aucune admission trouvée pour l'identifiant " );
		}
		
		AnalysisRequest analys = new AnalysisRequest();
		
		analys.setAnalysisNumber(getanalysisNumber());
		analys.setAdmission(admission);
		analys.setCreatedAt(new Date());
		analys.setObservation(analysDto.getObservation());
		analys.setPratician(getCurrentUserId());
		analys.setState('N');
		analys.setCreatedAt(new Date());
		analysisRequestRepository.save(analys);
		for(Long a : analysDto.getActs()) {
			Act act = actService.findActById(a).orElse(null);
			if (act == null) {
				throw new ResourceNotFoundByIdException("aucun acte trouvée pour l'identifiant "+ a  );
			}
			System.out.print(act.getName());
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
	public Optional<AnalysisRequest> findAnalysisRequestById(Long id) {
		return analysisRequestRepository.findById(id);
	}

	@Override
	public Page<AnalysisRequest> findAnalysisRequestsByPatient(Long patient, Pageable pageable) {
		return analysisRequestRepository.findAnalysisRequestByPatient(patient, pageable);
	}



	@Override
	public Page<AnalysisRequest> findAll(Pageable pageable) {
		return analysisRequestRepository.findAll(pageable);
	}

	@Override
	public Page<AnalysisRequest> findAllAnalysisRequests(String patientExternalId, String cnamNumber,
			String idCardNumber, String state, Pageable pageable) {
		return analysisRequestRepository.findAllAnalysisRequests(patientExternalId, cnamNumber, idCardNumber, state, pageable);
	}

	@Override
	public Page<AnalysisRequest> findAnalysisRequestsByAdmissionNumber(String admissionNumber, Pageable pageable) {
		return analysisRequestRepository.findAnalysisRequestsByAdmissionNumber(admissionNumber, pageable);
	}

	@Override
	public Long findAnalyseNumber(Long patientId) {
		return analysisRequestRepository.findAnalyseRequestNumber(patientId);
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
