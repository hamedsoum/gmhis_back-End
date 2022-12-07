package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.AnalysisRequestItem;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service @Transactional
public interface AnalysisRequestItemService {

	public AnalysisRequestItem saveAnalysisRequestItem(AnalysisRequestItem aItem) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	public List<AnalysisRequestItem> findAnalysisRequestItemsByPrescription(UUID analysisRequestId);
	
	public Optional<AnalysisRequestItem> findAnalysisRequestItemById(UUID id);


}
