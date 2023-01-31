package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;
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
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegister(@Param("cashRegister") Long cashRegister,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashier(@Param("cashier") Long cashier,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.state =:state")
	Page<CashRegisterManagement> findAllCashRegisterManagementByState(@Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.cashier.id =:cashier")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndCashier(@Param("cashRegister") Long cashRegister, @Param("cashier") Long cashier,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.state =:state")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndState(@Param("cashRegister") Long cashRegister, @Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier AND c.state =:state")
	Page<CashRegisterManagement> findAllCashierManagementByCashierAndState(@Param("cashier") Long cashier, @Param("state") Boolean state,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.cashier.id =:cashier AND c.state =:state")
	Page<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndCashierAndState(@Param("cashRegister") Long cashRegister, @Param("cashier") Long cashier, @Param("state") Boolean state,  Pageable pageable);
	
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashRegister.id =:cashRegister AND c.state = true")
	List<CashRegisterManagement> findAllCashRegisterManagementByCashRegisterAndStateOpenened(@Param("cashRegister") Long cashRegister);
	
	
	@Query(value = "SELECT c FROM CashRegisterManagement c where c.cashier.id =:cashier AND c.state = true")
	List<CashRegisterManagement> findAllCashierrManagementByCashierAndStateOpened(@Param("cashier") Long cashier);

}
