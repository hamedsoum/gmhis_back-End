package com.gmhis_backk.service;

import java.text.ParseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.BillHasInsured;

@Service
public interface BillHasInsuredService {
	
	public Page<BillHasInsured> findBillsHasInsuredByInsurance(Long Insurance,Pageable pageable);
	
	public Page<BillHasInsured>findBillsHasInsured(Pageable pageable);
	
	public Page<BillHasInsured>findBillsHasInsuredByDate(String date, Pageable pageable) throws ParseException ;
	
	public Page<BillHasInsured>findBillsHasInsuredByInsuranceIdAndDate(Long Insurance,String date, Pageable pageable) throws ParseException ;



}
