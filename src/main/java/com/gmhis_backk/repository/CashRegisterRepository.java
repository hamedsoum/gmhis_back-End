package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.CashRegister;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
	
	CashRegister findByName(String name);
	
	@Query(value = "SELECT c FROM CashRegister c WHERE active=1")
	List<CashRegister> findAllActive();
	
	@Query(value = "SELECT c FROM CashRegister c")
	Page<CashRegister>findAllCashRegister(Pageable pageable);
	
	@Query("SELECT c FROM CashRegister c WHERE c.active =:active AND c.name like %:name%")
	Page<CashRegister> findAllCashRegisterByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT c FROM CashRegister c WHERE c.name like %:name%")
	Page<CashRegister> findAllCashRegisterByName(String name, Pageable pageable);
	
	@Query("SELECT c FROM CashRegister c Where c.id =:id")
	ActGroup getCashRegisterDetails(Integer id);
	
	@Query("SELECT c FROM CashRegister c")
	List<CashRegister>findAllCashRegisterSimpleList();
	
	@Query(value = "SELECT c FROM CashRegister c where id= :id")
	Optional<CashRegister> findById(Long id);
}
