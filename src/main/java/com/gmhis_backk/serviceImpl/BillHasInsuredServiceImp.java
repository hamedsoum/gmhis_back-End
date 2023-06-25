package com.gmhis_backk.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.BillHasInsured;
import com.gmhis_backk.repository.BillHasInsuredRepository;
import com.gmhis_backk.service.BillHasInsuredService;

@Service
public class BillHasInsuredServiceImp implements BillHasInsuredService {

	@Autowired
	BillHasInsuredRepository billHasInsuredRepository;
	@Override
	public Page<BillHasInsured> findBillsHasInsuredByInsurance(Long Insurance,Pageable pageable) {
		return billHasInsuredRepository.findBillsHasInsuredByInsurance(Insurance, pageable);
	}

	@Override
	public Page<BillHasInsured> findBillsHasInsured(Pageable pageable) {
		return billHasInsuredRepository.findAll(pageable);
	}

	@Override
	public Page<BillHasInsured> findBillsHasInsuredByDate(String date, Pageable pageable) throws ParseException {
		String[] dates = date.split(","); 
		return billHasInsuredRepository.findByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}

	@Override
	public Page<BillHasInsured> findBillsHasInsuredByInsuranceIdAndDate(Long insurance, String date, Pageable pageable)
			throws ParseException {
		String[] dates = date.split(","); 
		return billHasInsuredRepository.findByBillhaInsuredInsuranceiDAndDate(insurance,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}

}
