package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.AnalysisRequestItem;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AnalysisRequestItemRepository;
import com.gmhis_backk.service.AnalysisRequestItemService;

@Service
public class AnalysisRequestItemServiceImpl implements AnalysisRequestItemService {

	@Autowired
	AnalysisRequestItemRepository analysisRequestItemRepository;
	@Override
	public AnalysisRequestItem saveAnalysisRequestItem(AnalysisRequestItem pItem)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		return analysisRequestItemRepository.save(pItem);
	}

	@Override
	public List<AnalysisRequestItem> findAnalysisRequestItemsByAnalysisRequest(Long analysisRequestId) {
		return analysisRequestItemRepository.findAnalysisRequestItemByAnalysisRequest(analysisRequestId);
	}

	@Override
	public Optional<AnalysisRequestItem> findAnalysisRequestItemById(UUID id) {
		return analysisRequestItemRepository.findById(id);
	}

}
