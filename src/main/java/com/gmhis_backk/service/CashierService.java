package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Cashier;
import com.gmhis_backk.domain.CashierCreate;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.CashierRepository;
import com.gmhis_backk.repository.UserRepository;

@Service
@Transactional
public class CashierService {
  
	@Autowired
	private  CashierRepository cashierRepository; 
	
	@Autowired
	private  UserService userService; 
	
	@Autowired
	private UserRepository userRepository;

	
	protected  User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	protected List<Map<String, Object>> map(List<Cashier> cashiers) {
		List<Map<String, Object>> cashierList = new ArrayList<>();
		
		cashiers.stream().forEach(cashier -> {
			Map<String, Object> billMap = new HashMap<>();
			billMap.put("id", cashier.getId());
			billMap.put("userID", cashier.getUser().getId());
			billMap.put("createdAt", cashier.getCreatedAt());
			billMap.put("active", cashier.getActive());
			billMap.put("firstName", cashier.getUser().getFirstName());
			billMap.put("lastName", cashier.getUser().getLastName());

			cashierList.add(billMap);
		});
		return cashierList;
	}
	
	public Cashier update(UUID cashierID, CashierCreate updateCashier) throws ResourceNotFoundByIdException {
		
		Cashier cashier = cashierRepository.findById(cashierID).orElse(null);
		if (cashier == null) throw new ResourceNotFoundByIdException("caissier Inexistant");
		
		User userRetrive = userService.findById(updateCashier.getUserID()).orElse(null);
		if (userRetrive == null) throw new ResourceNotFoundByIdException("Utilisateur Inexistant.");
		cashier.setUser(userRetrive);
		cashier.setUpdatedBy(getCurrentUser().getId());
		cashier.setUpdatededAt(new Date());
		BeanUtils.copyProperties(updateCashier,cashier,"id");

		return cashierRepository.save(cashier);
	}
	
	public Cashier Create(CashierCreate cashierCreate) throws ResourceNotFoundByIdException {
		
		User userRetrive = userService.findById(cashierCreate.getUserID()).orElse(null);
		if (userRetrive == null) throw new ResourceNotFoundByIdException("Utilisateur Inexistant.");
		
		Cashier cashier = new Cashier();
		BeanUtils.copyProperties(cashierCreate,cashier,"id");
		cashier.setUser(userRetrive);
		cashier.setCreatedAt(new Date());
		cashier.setCreatedBy(getCurrentUser().getId());
		
		return cashierRepository.save(cashier);
	}
	
	public Cashier retrieve(UUID cashierID) throws ResourceNotFoundByIdException {
		
		Cashier cashier = cashierRepository.findById(cashierID).orElse(null);
		if (cashier == null) throw new ResourceNotFoundByIdException("caissier Inexistant");
		 return cashier;
	}
	

	public ResponseEntity<Map<String, Object>>  search(Map<String, ?> cashierSearch) {
		
		Map<String, Object> response = new HashMap<>();
		
		int page = (int) cashierSearch.get("page");
		String[] sort = (String[]) cashierSearch.get("sort");
		int size = (int) cashierSearch.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Cashier> pCashiers = null;

		pCashiers = cashierRepository.findAll(getCurrentUser().getFacilityId(), pageable);
		
		List<Cashier> cashiers = pCashiers.getContent();
		
		List<Map<String, Object>> cashier = this.map(cashiers);
		response.put("items", cashier);
		response.put("totalElements", pCashiers.getTotalElements());
		response.put("totalPages", pCashiers.getTotalPages());
		response.put("size", pCashiers.getSize());
		response.put("pageNumber", pCashiers.getNumber());
		response.put("numberOfElements", pCashiers.getNumberOfElements());
		response.put("first", pCashiers.isFirst());
		response.put("last", pCashiers.isLast());
		response.put("empty", pCashiers.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
 
}
