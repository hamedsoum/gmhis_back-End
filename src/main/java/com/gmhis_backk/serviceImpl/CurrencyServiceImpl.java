package com.gmhis_backk.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Currency;
import com.gmhis_backk.repository.CurrencyRepository;
import com.gmhis_backk.service.CurrencyService;

/**
 * 
 * @author Mathurin
 *
 */
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepo;
	
	@Override
	public List<Currency> listAll() {
		return currencyRepo.listAll();
	}

}
