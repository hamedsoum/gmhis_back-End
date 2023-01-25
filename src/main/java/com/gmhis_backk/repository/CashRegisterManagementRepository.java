package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.CashRegisterManagement;


@Repository
public interface CashRegisterManagementRepository extends JpaRepository<CashRegisterManagement, UUID> {

//	@Query("Select c FROM CashRegisterManagement c")
//	List<CashRegisterManagement> findAllCashRegisterManagement();
//	
//	@Query(value = "SELECT c FROM CashRegisterManagement c where c.id =: id")
//	Optional<CashRegisterManagement> findById(UUID id);
	
	
}
