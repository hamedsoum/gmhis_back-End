package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.CashRegisterManagement;


@Repository
public interface CashRegisterManagementRepository extends JpaRepository<CashRegisterManagement, UUID> {

	@Query("Select c FROM CashRegisterManagement c")
	List<CashRegisterManagement> findAllCashRegisterManagement();
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister  ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegister(@Param("cashRegister") Long cashRegister,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier  ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashier(@Param("cashier") Long cashier,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.state =:state ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashRegisterManagementByState(@Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.cashier.id =:cashier ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndCashier(@Param("cashRegister") Long cashRegister, @Param("cashier") Long cashier,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.state =:state ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndState(@Param("cashRegister") Long cashRegister, @Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier AND c.state =:state ORDER BY c.openingDate DESC")
	Page<CashRegisterManagement> findAllCashierManagementByCashierAndState(@Param("cashier") Long cashier, @Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.cashier.id =:cashier AND c.state =:state")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndCashierAndState(@Param("cashRegister") Long cashRegister, @Param("cashier") Long cashier, @Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.state = true ORDER BY c.openingDate DESC")
	List<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndStateOpenened(@Param("cashRegister") Long cashRegister);

	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier AND c.state = true ORDER BY c.openingDate DESC")
	List<CashRegisterManagement> findAllCashierManagementByCashierAndStateOpened(@Param("cashier") Long cashier);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier AND c.state = true ORDER BY c.openingDate DESC")
	CashRegisterManagement getCashierManagementByCashier(@Param("cashier") Long cashier);

	@Query(value="SELECT c FROM CashRegisterManagement c WHERE c.cashier.id =:cashier AND c.state = true  ORDER BY c.openingDate DESC")
	public List<CashRegisterManagement> getOpenCaByCashier(@Param("cashier") Long cashier);
}
