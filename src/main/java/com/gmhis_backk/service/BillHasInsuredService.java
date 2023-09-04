package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.BillHasInsured;

@Service
public interface BillHasInsuredService {
	
	public Page<BillHasInsured>findBillsHasInsured(Pageable pageable);
	
	public List<BillHasInsured>findBillsHasInsuredByBillID(Long BillID);
	
}
