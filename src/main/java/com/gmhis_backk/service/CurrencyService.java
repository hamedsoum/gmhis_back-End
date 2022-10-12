package com.gmhis_backk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Currency;

/**
 * 
 * @author Mathurin
 *
 */
@Service
public interface CurrencyService {

	List<Currency> listAll();
}
