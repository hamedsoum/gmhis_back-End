package com.gmhis_backk.serviceImpl;

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
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AnalysisRequestDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
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
		System.out.print(admission.getAdmissionNumber());
		for(Long a : analysDto.getActs()) {
			AnalysisRequest analys = new AnalysisRequest();
			Act act = actService.findActById(a).orElse(null);
			if (act == null) {
				throw new ResourceNotFoundByIdException("aucune acte trouvée pour l'identifiant "+ a  );
			}
			analys.setAct(act);
			analys.setAdmission(admission);
			analys.setCreatedAt(new Date());
			analys.setObservation(analysDto.getObservation());
			analys.setPratician(getCurrentUserId());
			analysisRequestRepository.save(analys);
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
	public Page<AnalysisRequest> findAllAnalysisRequests(String firstName, String lastName, String patientExternalId,
			String cellPhone, String cnamNumber, String idCardNumber, String state, Pageable pageable) {
		return analysisRequestRepository.findAllAnalysisRequests(firstName, lastName, patientExternalId, cellPhone, cnamNumber, idCardNumber, state, pageable);
	}

	
}
