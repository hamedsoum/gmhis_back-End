package com.gmhis_backk.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.AnalysisRequest;
import com.gmhis_backk.dto.AnalysisRequestDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface AnalysisRequestService {

    public AnalysisRequest saveAnalysisRequest(AnalysisRequestDTO a)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Optional<AnalysisRequest> findAnalysisRequestById(UUID id);
	
	public Page<AnalysisRequest> findAnalysisRequestsByPatient(Long patient, Pageable pageable);
	
	public Page<AnalysisRequest> findAnalysisRequestsByAdmissionNumber(String admissionNumber, Pageable pageable);

	
	public Page<AnalysisRequest> findAll (Pageable pageable);
	
//	public Page<AnalysisRequest> findAllAnalysisRequests(String firstName, String lastName, String patientExternalId,
//			String cellPhone, String cnamNumber, String idCardNumber, String state, Pageable pageable);
	
	public Page<AnalysisRequest> findAllAnalysisRequests(String patientExternalId, String cnamNumber, String idCardNumber, String state, Pageable pageable);

	public Long findAnalyseNumber(Long patientId);

}
