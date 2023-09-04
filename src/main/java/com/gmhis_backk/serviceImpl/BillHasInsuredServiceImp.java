package com.gmhis_backk.serviceImpl;

import java.util.List;

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
	private BillHasInsuredRepository billHasInsuredRepository;
	
	@Override
	public Page<BillHasInsured> findBillsHasInsured(Pageable pageable) {
		return billHasInsuredRepository.findBillsHasInsured(pageable);
	}

	@Override
	public List<BillHasInsured> findBillsHasInsuredByBillID(Long BillID) {
		return billHasInsuredRepository.findBillsHasInsuredByBillID(BillID);
	}

}
